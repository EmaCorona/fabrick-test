package it.corona.fabrick.service;

import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.response.FabrickResponse;

public interface FabrickService {
    FabrickResponse<BankAccount> getBankAccount(Long accountId);
    FabrickResponse<Balance> getBankAccountBalance(Long accountId);
}
