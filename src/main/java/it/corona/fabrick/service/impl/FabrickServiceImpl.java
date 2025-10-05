package it.corona.fabrick.service.impl;

import it.corona.fabrick.component.FabrickClient;
import it.corona.fabrick.enums.ApiError;
import it.corona.fabrick.enums.FabrickStatus;
import it.corona.fabrick.exception.ApplicationException;
import it.corona.fabrick.mapper.TransactionMapper;
import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.dto.Transaction;
import it.corona.fabrick.model.dto.TransactionPayload;
import it.corona.fabrick.model.entity.TransactionEntity;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.repository.TransactionRepository;
import it.corona.fabrick.service.FabrickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabrickServiceImpl implements FabrickService {

    private final FabrickClient fabrickClient;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    @Override
    public FabrickResponse<BankAccount> getBankAccount(Long accountId) {
        log.info("Retrieving bank account cash for accountId: {}", accountId);
        final FabrickResponse<BankAccount> response = fabrickClient.getBankAccount(accountId);

        if (response == null) {
            String message = String.format("Received null response for bank account request with accountId: %s", accountId);
            log.error(message);
            throw new ApplicationException(ApiError.BANK_ACCOUNT_ERROR, message);
        }

        if (response.getStatus().equals(FabrickStatus.KO)) {
            String message = String.format(
                    "Received an error response for bank account request with accountId: %s, errors: %s",
                    accountId, (!CollectionUtils.isEmpty(response.getError())) ? response.getError().toString() : "Unknown error"
            );

            handleFabrickError(response, message, ApiError.BANK_ACCOUNT_ERROR);
        }

        log.info("Successfully retrieved bank account for accountId: {}", accountId);

        return response;
    }

    @Override
    public FabrickResponse<Balance> getBankAccountBalance(Long accountId) {
        log.info("Retrieving account balance for accountId: {}", accountId);
        final FabrickResponse<Balance> response = fabrickClient.getBankAccountBalance(accountId);

        if (response == null) {
            String message = String.format("Received null response for account balance request with accountId: %s", accountId);
            log.error(message);
            throw new ApplicationException(ApiError.BANK_ACCOUNT_BALANCE_ERROR, message);
        }

        if (response.getStatus().equals(FabrickStatus.KO)) {
            String message = String.format(
                    "Received an error response for account balance request with accountId: %s, errors: %s",
                    accountId, (!CollectionUtils.isEmpty(response.getError())) ? response.getError().toString() : "Unknown error"
            );

            handleFabrickError(response, message, ApiError.BANK_ACCOUNT_BALANCE_ERROR);
        }

        log.info("Successfully retrieved account balance for accountId: {}", accountId);

        return response;
    }

    @Override
    public FabrickResponse<TransactionPayload> getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        log.info("Retrieving transactions for accountId: {}, from {} to {}", accountId, fromAccountingDate, toAccountingDate);
        final FabrickResponse<TransactionPayload> response = fabrickClient.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);

        if (response == null) {
            String message = String.format("Received null response for account transactions request with accountId: %s", accountId);
            log.error(message);
            throw new ApplicationException(ApiError.BANK_ACCOUNT_TRANSACTION_ERROR, message);
        }

        if (response.getStatus().equals(FabrickStatus.KO)) {
            String message = String.format(
                    "Received an error response for account transactions request with accountId: %s, errors: %s",
                    accountId, (!CollectionUtils.isEmpty(response.getError())) ? response.getError().toString() : "Unknown error"
            );

            handleFabrickError(response, message, ApiError.BANK_ACCOUNT_TRANSACTION_ERROR);
        }

        log.info("Successfully retrieved {} transactions for accountId: {}", response.getPayload().getList().size(), accountId);
        saveTransactions(response.getPayload().getList(), accountId, fromAccountingDate, toAccountingDate);

        return response;
    }

    protected void saveTransactions(List<Transaction> transactions, Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        log.info("Saving transactions for accountId: {}", accountId);

        if (!CollectionUtils.isEmpty(transactions)) {
            List<TransactionEntity> existingEntities = transactionRepository.findTransactionsByAccountIdAndDateBetween(
                    accountId, fromAccountingDate, toAccountingDate
            );

            Map<String, TransactionEntity> existingTrxMap = existingEntities.stream()
                    .collect(Collectors.toMap(
                            trx -> trx.getExternalId() + "-" + trx.getOperationId(),
                            trx -> trx
                    ));

            for (Transaction transaction : transactions) {
                TransactionEntity trxToSave = transactionMapper.toEntity(transaction, accountId);
                String key = trxToSave.getExternalId() + "-" + trxToSave.getOperationId();

                if (existingTrxMap.containsKey(key)) {
                    log.debug("Updating transaction with transactionId: {} and operationId: {}", transaction.getTransactionId(), transaction.getOperationId());
                    trxToSave.setId(existingTrxMap.get(key).getId());
                }

                transactionRepository.save(trxToSave);
            }
        }
    }

    private <T> void handleFabrickError(FabrickResponse<T> response, String message, ApiError error) {
        log.error(message);

        // Check on response.getError() because even when it's KO, it returns null
        throw (!CollectionUtils.isEmpty(response.getError())) ?
                new ApplicationException(response.getError()) :
                new ApplicationException(error, message);
    }
}