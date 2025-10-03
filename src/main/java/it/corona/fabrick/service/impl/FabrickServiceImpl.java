package it.corona.fabrick.service.impl;

import it.corona.fabrick.component.FabrickClient;
import it.corona.fabrick.enums.ApiError;
import it.corona.fabrick.enums.FabrickStatus;
import it.corona.fabrick.exception.ApplicationException;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.service.FabrickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            throw new ApplicationException(ApiError.BANKING_ACCOUNT_CASH_ERROR, String.format(message, accountId));
        }

        if (response.getStatus().equals(FabrickStatus.KO)) {
            // Controllo su response.getError() in quanto anche con KO arriva null
            String message = String.format(
                    "Received an error response for bank account request with accountId: %s, errors: %s",
                    accountId, (response.getError() != null) ? response.getError().toString() : "Unknown error"
            );

            log.error(message);

            throw (response.getError() != null && !response.getError().isEmpty()) ?
                    new ApplicationException(response.getError()) :
                    new ApplicationException(ApiError.BANKING_ACCOUNT_CASH_ERROR, message);
        }

        log.info("Successfully retrieved bank account for accountId: {}", accountId);

        return response;
    }
}