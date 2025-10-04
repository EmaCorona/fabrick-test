package it.corona.fabrick.controller;

import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.dto.Transaction;
import it.corona.fabrick.model.dto.TransactionPayload;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.service.FabrickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts/{accountId}")
public class FabrickController {

    private final FabrickService fabrickService;

    // TODO: Documentazione swagger

    @GetMapping
    public ResponseEntity<FabrickResponse<BankAccount>> getBankAccount(@PathVariable Long accountId) {
        long start = System.currentTimeMillis();
        FabrickResponse<BankAccount> responseBody = fabrickService.getBankAccount(accountId);
        log.info("Request completed in {} ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/balance")
    public ResponseEntity<FabrickResponse<Balance>> getBankAccountBalance(@PathVariable Long accountId) {
        long start = System.currentTimeMillis();
        FabrickResponse<Balance> responseBody = fabrickService.getBankAccountBalance(accountId);
        log.info("Request completed in {} ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/transactions")
    public ResponseEntity<FabrickResponse<TransactionPayload>> getAccountTransactions(@PathVariable Long accountId,
                                                                                      @RequestParam LocalDate fromAccountingDate,
                                                                                      @RequestParam LocalDate toAccountingDate) {
        long start = System.currentTimeMillis();
        FabrickResponse<TransactionPayload> responseBody = fabrickService.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
        log.info("Request completed in {} ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(responseBody);
    }
}