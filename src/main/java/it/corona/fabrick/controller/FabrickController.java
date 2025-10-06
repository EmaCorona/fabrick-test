package it.corona.fabrick.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.dto.TransactionPayload;
import it.corona.fabrick.model.request.PaymentRequest;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.model.dto.moneytransfer.MoneyTransfer;
import it.corona.fabrick.service.FabrickService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts/{accountId}")
public class FabrickController {

    private final FabrickService fabrickService;

    @GetMapping
    @Operation(summary = "Get the bank account of the specified accountId")
    public ResponseEntity<FabrickResponse<BankAccount>> getBankAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(fabrickService.getBankAccount(accountId));
    }

    @GetMapping("/balance")
    @Operation(summary = "Get the current balance of the specified bank account")
    public ResponseEntity<FabrickResponse<Balance>> getBankAccountBalance(@PathVariable Long accountId) {
        return ResponseEntity.ok(fabrickService.getBankAccountBalance(accountId));
    }

    @GetMapping("/transactions")
    @Operation(summary = "Get the list of transactions for a bank account between two accounting dates")
    public ResponseEntity<FabrickResponse<TransactionPayload>> getAccountTransactions(@PathVariable Long accountId,
                                                                                      @RequestParam LocalDate fromAccountingDate,
                                                                                      @RequestParam LocalDate toAccountingDate) {
        return ResponseEntity.ok(fabrickService.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate));
    }

    @PostMapping("/money-transfer")
    @Operation(summary = "Create a money transfer for the specified bank account")
    public ResponseEntity<FabrickResponse<MoneyTransfer>> createMoneyTransfer(@RequestBody @Valid PaymentRequest request, @PathVariable Long accountId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fabrickService.createMoneyTransfer(request, accountId));
    }
}