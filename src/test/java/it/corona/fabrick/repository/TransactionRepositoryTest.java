package it.corona.fabrick.repository;

import it.corona.fabrick.enums.Enumeration;
import it.corona.fabrick.model.entity.TransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Long ACCOUNT_ID_1 = 1234L;
    private static final Long ACCOUNT_ID_2 = 9876L;

    private final LocalDate accountigDate = LocalDate.of(2019, 5, 1);

    private final LocalDate fromAccountingDate = LocalDate.of(2019, 1, 1);
    private final LocalDate toAccountingDate = LocalDate.of(2019, 12, 1);

    private final TransactionEntity t1 = new TransactionEntity();
    private final TransactionEntity t2 = new TransactionEntity();
    private final TransactionEntity t3 = new TransactionEntity();

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();

        t1.setExternalId("ext-001");
        t1.setOperationId("op-001");
        t1.setAccountingDate(accountigDate);
        t1.setValueDate(LocalDate.of(2019, 10, 2));
        t1.setEnumeration(Enumeration.GBS_TRANSACTION_TYPE);
        t1.setValue("TRX_VALUE_1");
        t1.setAmount(BigDecimal.valueOf(100.00));
        t1.setCurrency("EUR");
        t1.setDescription("Transaction 1");
        t1.setAccountId(ACCOUNT_ID_1);
        transactionRepository.save(t1);

        t2.setExternalId("ext-002");
        t2.setOperationId("op-002");
        t2.setAccountingDate(accountigDate);
        t2.setValueDate(LocalDate.of(2019, 10, 5));
        t2.setEnumeration(Enumeration.GBS_TRANSACTION_TYPE);
        t2.setValue("TRX_VALUE_2");
        t2.setAmount(BigDecimal.valueOf(200.00));
        t2.setCurrency("EUR");
        t2.setDescription("Transaction 2");
        t2.setAccountId(ACCOUNT_ID_1);
        transactionRepository.save(t2);

        t3.setExternalId("ext-003");
        t3.setOperationId("op-003");
        t3.setAccountingDate(accountigDate);
        t3.setValueDate(LocalDate.of(2019, 10, 4));
        t3.setEnumeration(Enumeration.GBS_TRANSACTION_TYPE);
        t3.setValue("TRX_VALUE_3");
        t3.setAmount(BigDecimal.valueOf(300.00));
        t3.setCurrency("EUR");
        t3.setDescription("Transaction 3");
        t3.setAccountId(ACCOUNT_ID_2);
        transactionRepository.save(t3);
    }

    @Test
    void givenTwoTransactionForAccountId_findTransactionsByAccountIdAndDateBetween_returnsTwoTransactions() {
        /* ***************** ACT ***************** */
        List<TransactionEntity> results = transactionRepository.findTransactionsByAccountIdAndDateBetween(
                ACCOUNT_ID_1,
                fromAccountingDate,
                toAccountingDate
        );

        /* ***************** ASSERT ***************** */
        assertEquals(2, results.size());

        // Assertions: accountId
        assertEquals(ACCOUNT_ID_1, results.getFirst().getAccountId());
        assertEquals(ACCOUNT_ID_1, results.getLast().getAccountId());

        // Assertions: externalId
        assertEquals(t1.getExternalId(), results.getFirst().getExternalId());
        assertEquals(t2.getExternalId(), results.getLast().getExternalId());

        // Assertions: operationId
        assertEquals(t1.getOperationId(), results.getFirst().getOperationId());
        assertEquals(t2.getOperationId(), results.getLast().getOperationId());

        // Assertions: amount
        assertEquals(BigDecimal.valueOf(100.00), results.getFirst().getAmount());
        assertEquals(BigDecimal.valueOf(200.00), results.getLast().getAmount());
    }

    @Test
    void givenNoMatching_findTransactionsByAccountIdAndDateBetween_returnsEmptyList() {
        /* ***************** ACT ***************** */
        List<TransactionEntity> results = transactionRepository.findTransactionsByAccountIdAndDateBetween(
                ACCOUNT_ID_1,
                fromAccountingDate.plusYears(10),
                toAccountingDate.plusYears(10)
        );

        /* ***************** ASSERT ***************** */
        assertEquals(0, results.size());
    }

    @Test
    void givenOneTransactionForAccountId_findTransactionByAccountIdAndDateBetween_returnsTheTransaction() {
        /* ***************** ACT ***************** */
        List<TransactionEntity> results = transactionRepository.findTransactionsByAccountIdAndDateBetween(
                ACCOUNT_ID_2,
                fromAccountingDate,
                toAccountingDate
        );

        /* ***************** ASSERT ***************** */
        assertEquals(1, results.size());

        // Assertions: accountId
        assertEquals(ACCOUNT_ID_2, results.getFirst().getAccountId());

        // Assertions: externalId
        assertEquals(t3.getExternalId(), results.getFirst().getExternalId());

        // Assertions: operationId
        assertEquals(t3.getOperationId(), results.getFirst().getOperationId());

        // Assertions: amount
        assertEquals(BigDecimal.valueOf(300.00), results.getFirst().getAmount());
    }
}
