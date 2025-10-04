package it.corona.fabrick.service;

import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.dto.Transaction;
import it.corona.fabrick.model.dto.TransactionPayload;
import it.corona.fabrick.model.response.FabrickResponse;

import java.time.LocalDate;
import java.util.List;

public interface FabrickService {
    FabrickResponse<BankAccount> getBankAccount(Long accountId);
    FabrickResponse<Balance> getBankAccountBalance(Long accountId);
    FabrickResponse<TransactionPayload> getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate);
}
