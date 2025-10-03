package it.corona.fabrick.service.impl;

import it.corona.fabrick.component.FabrickClient;
import it.corona.fabrick.enums.ApiError;
import it.corona.fabrick.enums.FabrickStatus;
import it.corona.fabrick.exception.ApplicationException;
import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.service.FabrickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabrickServiceImpl implements FabrickService {

    private final FabrickClient fabrickClient;

    @Override
    public FabrickResponse<BankAccount> getBankAccount(Long accountId) {
        log.info("Retrieving bank account cash for accountId: {}", accountId);
        final FabrickResponse<BankAccount> response = fabrickClient.getBankAccount(accountId);

        if (response == null) {
            String message = String.format("Received null response for bank account request with accountId: %s", accountId);
            log.error(message);
            throw new ApplicationException(ApiError.BANK_ACCOUNT_ERROR, String.format(message, accountId));
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
            throw new ApplicationException(ApiError.BANK_ACCOUNT_BALANCE_ERROR, String.format(message, accountId));
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

    private <T> void handleFabrickError(FabrickResponse<T> response, String message, ApiError error) {
        log.error(message);

        // Controllo su response.getError() in quanto anche con KO arriva null
        throw (!CollectionUtils.isEmpty(response.getError())) ?
                new ApplicationException(response.getError()) :
                new ApplicationException(error, message);
    }
}