package it.corona.fabrick.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.corona.fabrick.MockUtils;
import it.corona.fabrick.enums.FabrickStatus;
import it.corona.fabrick.exception.ApplicationException;
import it.corona.fabrick.model.dto.Balance;
import it.corona.fabrick.model.dto.BankAccount;
import it.corona.fabrick.model.dto.FabrickError;
import it.corona.fabrick.model.dto.TransactionPayload;
import it.corona.fabrick.model.dto.MoneyTransfer;
import it.corona.fabrick.model.request.PaymentRequest;
import it.corona.fabrick.model.response.FabrickResponse;
import it.corona.fabrick.service.FabrickService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FabrickController.class)
public class FabrickControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FabrickService fabrickService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/api/v1/accounts/{accountId}";

    private static final String GET_BANK_ACCOUNT_ENDPOINT = BASE_PATH;

    private static final String GET_ACCOUNT_BALANCE_ENDPOINT = BASE_PATH + "/balance";

    private static final String GET_ACCOUNT_TRANSACTIONS_ENDPOINT = BASE_PATH + "/transactions";

    private static final String CREATE_MONEY_TRANSFER_ENDPOINT = BASE_PATH + "/money-transfer";

    private static final Long ACCOUNT_ID = 1234L;

    // API: getBankAccount

    @Test
    void givenValidRequest_getBankAccount_shouldReturnOk() throws Exception {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<BankAccount> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.OK);
        response.setPayload(MockUtils.getMockedBankAccount());

        when(fabrickService.getBankAccount(anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_BANK_ACCOUNT_ENDPOINT, ACCOUNT_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        /* ***************** ASSERT ***************** */
        verify(fabrickService).getBankAccount(eq(ACCOUNT_ID));
    }

    @Test
    void givenInvalidAccountId_getBankAccount_shouldReturnBadRequest() throws Exception {
        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_BANK_ACCOUNT_ENDPOINT, "XYZ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        /* ***************** ASSERT ***************** */
        verify(fabrickService, never()).getBankAccount(eq(ACCOUNT_ID));
    }

    @Test
    void givenError_getBankAccount_shouldReturnInternalServerError() throws Exception {
        /* ***************** ARRANGE ***************** */
        FabrickError error = new FabrickError();
        error.setCode("500");
        error.setDescription("DESCRIPTION");
        error.setParams("PARAMS");

        FabrickResponse<BankAccount> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);
        response.setErrors(List.of(error));

        when(fabrickService.getBankAccount(anyLong()))
                .thenThrow(new ApplicationException(response.getErrors()));

        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_BANK_ACCOUNT_ENDPOINT, ACCOUNT_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        /* ***************** ASSERT ***************** */
        verify(fabrickService).getBankAccount(eq(ACCOUNT_ID));
    }

    // API: getAccountBalance

    @Test
    void givenValidRequest_getAccountBalance_shouldReturnOk() throws Exception {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<Balance> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.OK);
        response.setPayload(MockUtils.getMockedBalance());

        when(fabrickService.getBankAccountBalance(anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_ACCOUNT_BALANCE_ENDPOINT, ACCOUNT_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        /* ***************** ASSERT ***************** */
        verify(fabrickService).getBankAccountBalance(eq(ACCOUNT_ID));
    }

    @Test
    void givenInvalidAccountId_getAccountBalance_shouldReturnBadRequest() throws Exception {
        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_ACCOUNT_BALANCE_ENDPOINT, "XYZ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        /* ***************** ASSERT ***************** */
        verify(fabrickService, never()).getBankAccountBalance(eq(ACCOUNT_ID));
    }

    @Test
    void givenError_getAccountBalance_shouldReturnInternalServerError() throws Exception {
        /* ***************** ARRANGE ***************** */
        FabrickError error = new FabrickError();
        error.setCode("500");
        error.setDescription("DESCRIPTION");
        error.setParams("PARAMS");

        FabrickResponse<Balance> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);
        response.setErrors(List.of(error));

        when(fabrickService.getBankAccountBalance(anyLong()))
                .thenThrow(new ApplicationException(response.getErrors()));

        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_ACCOUNT_BALANCE_ENDPOINT, ACCOUNT_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        /* ***************** ASSERT ***************** */
        verify(fabrickService).getBankAccountBalance(eq(ACCOUNT_ID));
    }

    // API: getAccountTransactions

    @Test
    void givenValidRequest_getAccountTransactions_shouldReturnOk() throws Exception {
        /* ***************** ARRANGE ***************** */
        FabrickResponse<TransactionPayload> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.OK);
        response.setPayload(MockUtils.getMockedTransactionPayload());

        when(fabrickService.getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(response);

        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_ACCOUNT_TRANSACTIONS_ENDPOINT, ACCOUNT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("fromAccountingDate", LocalDate.now().toString())
                        .queryParam("toAccountingDate", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        /* ***************** ASSERT ***************** */
        verify(fabrickService).getAccountTransactions(eq(ACCOUNT_ID), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void givenInvalidAccountId_getAccountTransactions_shouldReturnBadRequest() throws Exception {
        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_ACCOUNT_TRANSACTIONS_ENDPOINT, "XYZ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        /* ***************** ASSERT ***************** */
        verify(fabrickService, never()).getAccountTransactions(eq(ACCOUNT_ID), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void givenError_getAccountTransactions_shouldReturnInternalServerError() throws Exception {
        /* ***************** ARRANGE ***************** */
        FabrickError error = new FabrickError();
        error.setCode("500");
        error.setDescription("DESCRIPTION");
        error.setParams("PARAMS");

        FabrickResponse<TransactionPayload> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);
        response.setErrors(List.of(error));

        when(fabrickService.getAccountTransactions(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenThrow(new ApplicationException(response.getErrors()));

        /* ***************** ACT ***************** */
        mockMvc.perform(get(GET_ACCOUNT_TRANSACTIONS_ENDPOINT, ACCOUNT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("fromAccountingDate", LocalDate.now().toString())
                        .queryParam("toAccountingDate", LocalDate.now().toString())
                        .content(objectMapper.writeValueAsString(new PaymentRequest())))
                .andExpect(status().isInternalServerError());

        /* ***************** ASSERT ***************** */
        verify(fabrickService).getAccountTransactions(eq(ACCOUNT_ID), any(LocalDate.class), any(LocalDate.class));
    }

    // API: createMoneyTransfer

    @Test
    void givenValidRequest_createMoneyTransfer_shouldReturnCreated() throws Exception {
        /* ***************** ARRANGE ***************** */
        PaymentRequest request = MockUtils.getMockedPaymentRequest();

        FabrickResponse<MoneyTransfer> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.OK);
        response.setPayload(MockUtils.getMockedMoneyTransfer());

        when(fabrickService.createMoneyTransfer(any(PaymentRequest.class), anyLong())).thenReturn(response);

        /* ***************** ACT ***************** */
        mockMvc.perform(post(CREATE_MONEY_TRANSFER_ENDPOINT, ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        /* ***************** ASSERT ***************** */
        verify(fabrickService).createMoneyTransfer(any(PaymentRequest.class), eq(ACCOUNT_ID));
    }

    @Test
    void givenNullRequest_createMoneyTransfer_shouldReturnBadRequest() throws Exception {
        /* ***************** ACT ***************** */
        mockMvc.perform(post(CREATE_MONEY_TRANSFER_ENDPOINT, ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

        /* ***************** ASSERT ***************** */
        verify(fabrickService, never()).createMoneyTransfer(any(PaymentRequest.class), eq(ACCOUNT_ID));
    }

    @Test
    void givenError_createMoneyTransfer_shouldReturnInternalServerError() throws Exception {
        /* ***************** ARRANGE ***************** */
        PaymentRequest request = MockUtils.getMockedPaymentRequest();

        FabrickError error = new FabrickError();
        error.setCode("500");
        error.setDescription("DESCRIPTION");
        error.setParams("PARAMS");

        FabrickResponse<MoneyTransfer> response = new FabrickResponse<>();
        response.setStatus(FabrickStatus.KO);
        response.setErrors(List.of(error));

        when(fabrickService.createMoneyTransfer(any(PaymentRequest.class), anyLong())).thenThrow(new ApplicationException(response.getErrors()));

        /* ***************** ACT ***************** */
        mockMvc.perform(post(CREATE_MONEY_TRANSFER_ENDPOINT, ACCOUNT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());

        /* ***************** ASSERT ***************** */
        verify(fabrickService).createMoneyTransfer(any(PaymentRequest.class), eq(ACCOUNT_ID));
    }
}