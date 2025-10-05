package it.corona.fabrick.component;

import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.dto.TransactionPayload;
import it.corona.fabrick.model.request.PaymentRequest;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.model.dto.moneytransfer.MoneyTransfer;
import jakarta.validation.Valid;

import java.time.LocalDate;

public interface FabrickClient {
    FabrickResponse<BankAccount> getBankAccount(Long accountId);
    FabrickResponse<Balance> getBankAccountBalance(Long accountId);
    FabrickResponse<TransactionPayload> getAccountTransactions(Long accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate);
    FabrickResponse<MoneyTransfer> createMoneyTransfer(@Valid PaymentRequest request, Long accountId);
}