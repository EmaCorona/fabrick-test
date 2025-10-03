package it.corona.fabrick.controller;

import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.service.FabrickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fabrick/accounts/{accountId}")
public class FabrickController {

    private final FabrickService fabrickService;

    // TODO: Documentazione swagger

    @GetMapping("/bank-account")
    public ResponseEntity<FabrickResponse<BankAccount>> getBankAccount(@PathVariable Long accountId) {
        long start = System.currentTimeMillis();
        FabrickResponse<BankAccount> responseBody = fabrickService.getBankAccount(accountId);
        log.info("Request completed in {} ms", System.currentTimeMillis() - start);
        return ResponseEntity.ok(responseBody);
    }
}