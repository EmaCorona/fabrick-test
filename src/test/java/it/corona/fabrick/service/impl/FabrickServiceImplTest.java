package it.corona.fabrick.service.impl;

import it.corona.fabrick.component.FabrickClient;
import it.corona.fabrick.enums.ApiError;
import it.corona.fabrick.enums.FabrickStatus;
import it.corona.fabrick.exception.ApplicationException;
import it.corona.fabrick.mapper.TransactionMapper;
import it.corona.fabrick.model.dto.*;
import it.corona.fabrick.model.entity.TransactionEntity;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FabrickServiceImplTest {

    @InjectMocks
    private FabrickServiceImpl fabrickService;

    @Mock
    private FabrickClient fabrickClient;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    private static final Long ACCOUNT_ID = 1234L;

    private final List<FabrickError> fabrickErrors = new ArrayList<>();

    private final LocalDate fromAccountingDate = LocalDate.parse("2019-01-01");

    private final LocalDate toAccountingDate = LocalDate.parse("2025-12-01");

    @BeforeEach
    void setUp() {
        FabrickError error = new FabrickError();
        error.setCode("CODE_1");
        error.setDescription("DESCRIPTION_1");
        error.setParams("PARAMS_1");

        FabrickError error2 = new FabrickError();
        error2.setCode("CODE_2");
        error2.setDescription("DESCRIPTION_2");
        error2.setParams("PARAMS_2");

        fabrickErrors.add(error);
        fabrickErrors.add(error2);
    }

    @Test
    void givenNullResponse_getBankAccount_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        when(fabrickClient.getBankAccount(anyLong())).thenReturn(null);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(ApplicationException.class, () -> fabrickService.getBankAccount(ACCOUNT_ID));

        /* ***************** ASSERT ***************** */
        assertEquals(ApiError.BANK_ACCOUNT_ERROR.getCode(), ex.getErrorResponse().getErrors().getFirst().getCode());
        verify(fabrickClient, times(1)).getBankAccount(ACCOUNT_ID);
    }

    @Test
    void givenStatusKO_getBankAccount_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<BankAccount> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);
        response.setError(fabrickErrors);

        when(fabrickClient.getBankAccount(anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(ApplicationException.class, () -> fabrickService.getBankAccount(ACCOUNT_ID));

        /* ***************** ASSERT ***************** */
        assertEquals(response.getError().getFirst().getCode(), ex.getErrorResponse().getErrors().getFirst().getCode());
        assertEquals(response.getError().getLast().getCode(), ex.getErrorResponse().getErrors().getLast().getCode());
        verify(fabrickClient, times(1)).getBankAccount(ACCOUNT_ID);
    }

    @Test
    void givenStatusKOWithoutErrors_getBankAccount_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<BankAccount> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);

        when(fabrickClient.getBankAccount(anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(ApplicationException.class, () -> fabrickService.getBankAccount(ACCOUNT_ID));

        /* ***************** ASSERT ***************** */
        assertEquals(ApiError.BANK_ACCOUNT_ERROR.getCode(), ex.getErrorResponse().getErrors().getLast().getCode());
        verify(fabrickClient, times(1)).getBankAccount(ACCOUNT_ID);
    }

    @Test
    void givenStatusOk_getBankAccount_ReturnsBankAccount() {
        /* ***************** ARRANGE ***************** */
        final BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountId("14537780");
        bankAccount.setIban("IT40L0326822311052923800661");
        bankAccount.setAbiCode("03268");
        bankAccount.setCabCode("22311");
        bankAccount.setCountryCode("IT");
        bankAccount.setInternationalCin("40");
        bankAccount.setNationalCin("L");
        bankAccount.setAccount("52923800661");
        bankAccount.setAlias("Test api");
        bankAccount.setProductName("Conto Websella");
        bankAccount.setHolderName("VALERIAS VELERIA ALBERTARIO ALBERTO");
        bankAccount.setActivatedDate(LocalDate.parse("2016-12-14"));
        bankAccount.setCurrency("EUR");

        FabrickResponse<BankAccount> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.OK);
        response.setPayload(bankAccount);

        when(fabrickClient.getBankAccount(anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        FabrickResponse<BankAccount> result = fabrickService.getBankAccount(ACCOUNT_ID);

        /* ***************** ASSERT ***************** */
        assertNotNull(result);
        assertEquals(FabrickStatus.OK, result.getStatus());
        assertEquals(bankAccount, result.getPayload());
        verify(fabrickClient, times(1)).getBankAccount(ACCOUNT_ID);
    }

    @Test
    void givenNullResponse_getBankAccountBalance_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        when(fabrickClient.getBankAccountBalance(anyLong())).thenReturn(null);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(ApplicationException.class, () -> fabrickService.getBankAccountBalance(ACCOUNT_ID));

        /* ***************** ASSERT ***************** */
        assertEquals(ApiError.BANK_ACCOUNT_BALANCE_ERROR.getCode(), ex.getErrorResponse().getErrors().getFirst().getCode());
        verify(fabrickClient, times(1)).getBankAccountBalance(ACCOUNT_ID);
    }

    @Test
    void givenStatusKO_getBankAccountBalance_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<Balance> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);
        response.setError(fabrickErrors);

        when(fabrickClient.getBankAccountBalance(anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(ApplicationException.class, () -> fabrickService.getBankAccountBalance(ACCOUNT_ID));

        /* ***************** ASSERT ***************** */
        assertEquals(response.getError().getFirst().getCode(), ex.getErrorResponse().getErrors().getFirst().getCode());
        assertEquals(response.getError().getLast().getCode(), ex.getErrorResponse().getErrors().getLast().getCode());
        verify(fabrickClient, times(1)).getBankAccountBalance(ACCOUNT_ID);
    }

    @Test
    void givenStatusKOWithoutErrors_getBankAccountBalance_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<Balance> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);

        when(fabrickClient.getBankAccountBalance(anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(ApplicationException.class, () -> fabrickService.getBankAccountBalance(ACCOUNT_ID));

        /* ***************** ASSERT ***************** */
        assertEquals(ApiError.BANK_ACCOUNT_BALANCE_ERROR.getCode(), ex.getErrorResponse().getErrors().getLast().getCode());
        verify(fabrickClient, times(1)).getBankAccountBalance(ACCOUNT_ID);
    }

    @Test
    void givenStatusOk_getBankAccountBalance_ReturnsBankAccount() {
        /* ***************** ARRANGE ***************** */
        Balance balance = Balance.builder()
                .date(LocalDate.parse("2025-10-04"))
                .balance(BigDecimal.valueOf(-16.79))
                .availableBalance(BigDecimal.valueOf(-16.79))
                .currency("EUR")
                .build();

        FabrickResponse<Balance> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.OK);
        response.setPayload(balance);

        when(fabrickClient.getBankAccountBalance(anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        FabrickResponse<Balance> result = fabrickService.getBankAccountBalance(ACCOUNT_ID);

        /* ***************** ASSERT ***************** */
        assertNotNull(result);
        assertEquals(FabrickStatus.OK, result.getStatus());
        assertEquals(balance, result.getPayload());
        verify(fabrickClient, times(1)).getBankAccountBalance(ACCOUNT_ID);
    }

    @Test
    void givenNullResponse_getAccountTransactions_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        when(fabrickClient.getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(null);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(
                ApplicationException.class, () -> fabrickService.getAccountTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate)
        );

        /* ***************** ASSERT ***************** */
        assertEquals(ApiError.BANK_ACCOUNT_TRANSACTION_ERROR.getCode(), ex.getErrorResponse().getErrors().getFirst().getCode());
        verify(fabrickClient, times(1)).getAccountTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate);
    }

    @Test
    void givenStatusKo_getAccountTransactions_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<TransactionPayload> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);
        response.setError(fabrickErrors);

        when(fabrickClient.getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(response);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(
                ApplicationException.class, () -> fabrickService.getAccountTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate)
        );

        /* ***************** ASSERT ***************** */
        assertEquals(response.getError().getFirst().getCode(), ex.getErrorResponse().getErrors().getFirst().getCode());
        assertEquals(response.getError().getLast().getCode(), ex.getErrorResponse().getErrors().getLast().getCode());
        verify(fabrickClient, times(1)).getAccountTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate);
    }

    @Test
    void givenStatusKoWithoutErrors_getAccountTransactions_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<TransactionPayload> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);

        when(fabrickClient.getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(response);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(
                ApplicationException.class, () -> fabrickService.getAccountTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate)
        );

        /* ***************** ASSERT ***************** */
        assertEquals(ApiError.BANK_ACCOUNT_TRANSACTION_ERROR.getCode(), ex.getErrorResponse().getErrors().getLast().getCode());
        verify(fabrickClient, times(1)).getAccountTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate);
    }

    @Test
    void givenStatusOk_getAccountTransactions_ReturnsTransactions() {
        /* ***************** ARRANGE ***************** */
        Transaction dto1 = Transaction.builder().transactionId("T1").operationId("O1").build();
        Transaction dto2 = Transaction.builder().transactionId("T2").operationId("O2").build();

        TransactionEntity entity1 = TransactionEntity.builder().externalId("T1").operationId("O1").build();
        TransactionEntity entity2 = TransactionEntity.builder().externalId("T1").operationId("O1").build();

        TransactionPayload payload = TransactionPayload.builder().list(List.of(dto1, dto2)).build();

        FabrickResponse<TransactionPayload> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.OK);
        response.setPayload(payload);

        when(transactionMapper.toEntity(dto1, ACCOUNT_ID)).thenReturn(entity1);
        when(transactionMapper.toEntity(dto2, ACCOUNT_ID)).thenReturn(entity2);
        when(fabrickClient.getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(response);

        /* ***************** ACT ***************** */
        FabrickResponse<TransactionPayload> result = fabrickService.getAccountTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate);

        /* ***************** ASSERT ***************** */
        assertNotNull(result);
        assertEquals(FabrickStatus.OK, result.getStatus());
        assertEquals(payload, result.getPayload());
        verify(fabrickClient, times(1)).getAccountTransactions(ACCOUNT_ID, fromAccountingDate, toAccountingDate);
    }

    @Test
    void givenNullTransactions_saveTransactions_shouldDoNothing() {
        /* ***************** ACT ***************** */
        fabrickService.saveTransactions(null, ACCOUNT_ID, fromAccountingDate, toAccountingDate);
        /* ***************** ASSERT ***************** */
        verifyNoInteractions(transactionMapper);
    }

    @Test
    void givenEmptyList_saveTransactions_shouldDoNothing() {
        /* ***************** ACT ***************** */
        fabrickService.saveTransactions(Collections.emptyList(), ACCOUNT_ID, fromAccountingDate, toAccountingDate);
        /* ***************** ASSERT ***************** */
        verifyNoInteractions(transactionRepository, transactionMapper);
    }

    @Test
    void givenNewTransactions_saveTransactions_shouldSave() {
        /* ***************** ARRANGE ***************** */
        Transaction dto = Transaction.builder()
                .transactionId("T1")
                .operationId("O1")
                .build();

        TransactionEntity entity = TransactionEntity.builder()
                .externalId("T1")
                .operationId("O1")
                .build();

        when(transactionMapper.toEntity(dto, ACCOUNT_ID)).thenReturn(entity);
        when(transactionRepository.findTransactionsByAccountIdAndDateBetween(ACCOUNT_ID, fromAccountingDate, toAccountingDate))
                .thenReturn(Collections.emptyList());

        /* ***************** ACT ***************** */
        fabrickService.saveTransactions(List.of(dto), ACCOUNT_ID, fromAccountingDate, toAccountingDate);

        /* ***************** ASSERT ***************** */
        verify(transactionRepository).save(entity);
    }

    @Test
    void givenExistingTransactions_saveTransactions_shouldUpdate() {
        /* ***************** ARRANGE ***************** */
        Transaction dto = Transaction.builder()
                .transactionId("T1")
                .operationId("O1")
                .build();

        TransactionEntity existingEntity = TransactionEntity.builder()
                .id(100L)
                .externalId("T1")
                .operationId("O1")
                .build();

        TransactionEntity trxToSave = TransactionEntity.builder()
                .externalId("T1")
                .operationId("O1")
                .build();

        when(transactionMapper.toEntity(dto, ACCOUNT_ID)).thenReturn(trxToSave);
        when(transactionRepository.findTransactionsByAccountIdAndDateBetween(ACCOUNT_ID, fromAccountingDate, toAccountingDate))
                .thenReturn(List.of(existingEntity));

        /* ***************** ACT ***************** */
        fabrickService.saveTransactions(List.of(dto), ACCOUNT_ID, fromAccountingDate, toAccountingDate);

        /* ***************** ASSERT ***************** */
        assertEquals(trxToSave.getId(), existingEntity.getId());
        verify(transactionRepository).save(trxToSave);
    }
}