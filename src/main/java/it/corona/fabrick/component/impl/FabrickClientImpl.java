package it.corona.fabrick.component.impl;

import it.corona.fabrick.component.ClientInterface;
import it.corona.fabrick.component.FabrickClient;
import it.corona.fabrick.config.properties.FabrickConfig;
import it.corona.fabrick.enums.ApiError;
import it.corona.fabrick.enums.FabrickHeader;
import it.corona.fabrick.exception.ApplicationException;
import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.dto.Transaction;
import it.corona.fabrick.model.dto.TransactionPayload;
import it.corona.fabrick.model.response.FabrickResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FabrickClientImpl implements FabrickClient {

    private final ClientInterface clientInterface;

    private final FabrickConfig fabrickConfig;

    @Override
    public FabrickResponse<BankAccount> getBankAccount(Long accountId) {
        try {
            return clientInterface.getRequest(
                    fabrickConfig.getBankAccount(),
                    new Object[]{accountId},
                    Map.of(),
                    getFabrickHeaders(),
                    new ParameterizedTypeReference<>() {}
            );
        } catch (WebClientResponseException ex) {
            String message = "Error while requesting banking account with accountId: {}, status: {}, body: {}";
            log.error(message, accountId, ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new ApplicationException(ApiError.EXTERNAL_CALL_ERROR, ex.getMessage());
        }
    }

    @Override
    public FabrickResponse<Balance> getBankAccountBalance(Long accountId) {
        try {
            return clientInterface.getRequest(
                    fabrickConfig.getBalance(),
                    new Object[]{accountId},
                    Map.of(),
                    getFabrickHeaders(),
                    new ParameterizedTypeReference<>() {}
            );
        } catch (WebClientResponseException ex) {
            String message = "Error while requesting account balance for accountId: {}, status: {}, body: {}";
            log.error(message, accountId, ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new ApplicationException(ApiError.EXTERNAL_CALL_ERROR, ex.getMessage());
        }
    }

    @Override
    public FabrickResponse<TransactionPayload> getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) {
        try {
            return clientInterface.getRequest(
                    fabrickConfig.getTransactions(),
                    new Object[]{accountId},
                    Map.of("fromAccountingDate", fromAccountingDate, "toAccountingDate", toAccountingDate),
                    getFabrickHeaders(),
                    new ParameterizedTypeReference<>() {}
            );
        } catch (WebClientResponseException ex) {
            String message = "Error while requesting account balance for accountId: {}, status: {}, body: {}";
            log.error(message, accountId, ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new ApplicationException(ApiError.EXTERNAL_CALL_ERROR, ex.getMessage());
        }
    }

    private Map<String, String> getFabrickHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(FabrickHeader.APY_KEY.getValue(), fabrickConfig.getApiKey());
        headers.put(FabrickHeader.AUTH_SCHEMA.getValue(), fabrickConfig.getAuthSchema());
        return headers;
    }
}