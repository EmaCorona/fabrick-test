package it.corona.fabrick.component;

import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.response.FabrickResponse;

public interface FabrickClient {
    FabrickResponse<BankAccount> getBankAccount(Long accountId);
}