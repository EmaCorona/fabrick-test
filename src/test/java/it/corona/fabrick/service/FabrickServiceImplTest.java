package it.corona.fabrick.service;

import it.corona.fabrick.component.FabrickClient;
import it.corona.fabrick.enums.ApiError;
import it.corona.fabrick.enums.FabrickStatus;
import it.corona.fabrick.exception.ApplicationException;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.dto.FabrickError;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.service.impl.FabrickServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FabrickServiceImplTest {

    @InjectMocks
    private FabrickServiceImpl fabrickService;

    @Mock
    private FabrickClient fabrickClient;

    private static final Long ACCOUNT_ID = 1234L;

    private final BankAccount bankAccount = new BankAccount();

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void givenNullResponse_getBankAccount_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        when(fabrickClient.getBankAccount(anyLong())).thenReturn(null);

        /* ***************** ACT ***************** */
        ApplicationException ex = assertThrows(ApplicationException.class, () -> fabrickService.getBankAccount(ACCOUNT_ID));

        /* ***************** ASSERT ***************** */
        assertEquals(ApiError.BANKING_ACCOUNT_CASH_ERROR.getCode(), ex.getErrorResponse().getErrors().getFirst().getCode());
        verify(fabrickClient, times(1)).getBankAccount(ACCOUNT_ID);
    }

    @Test
    void givenStatusKO_getBankAccount_throwApplicationException() {
        /* ***************** ARRANGE ***************** */
        FabrickError error = new FabrickError();
        error.setCode("CODE_1");
        error.setDescription("DESCRIPTION_1");
        error.setParams("PARAMS_1");

        FabrickError error2 = new FabrickError();
        error2.setCode("CODE_2");
        error2.setDescription("DESCRIPTION_2");
        error2.setParams("PARAMS_2");

        FabrickResponse<BankAccount> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);
        response.setError(List.of(error, error2));

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
        assertEquals(ApiError.BANKING_ACCOUNT_CASH_ERROR.getCode(), ex.getErrorResponse().getErrors().getLast().getCode());
        verify(fabrickClient, times(1)).getBankAccount(ACCOUNT_ID);
    }

    @Test
    void givenStatusOk_whenGetBankingAccountCash_thenReturnResponse() {
        /* ***************** ARRANGE ***************** */
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
}