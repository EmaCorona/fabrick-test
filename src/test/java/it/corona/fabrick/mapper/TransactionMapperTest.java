package it.corona.fabrick.mapper;

import it.corona.fabrick.enums.Enumeration;
import it.corona.fabrick.model.dto.Transaction;
import it.corona.fabrick.model.dto.TransactionType;
import it.corona.fabrick.model.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    void givenValidDto_toEntity_shouldReturnMappedEntity() {
        /* ***************** ARRANGE ***************** */
        final TransactionType type = TransactionType.builder()
                .enumeration(Enumeration.GBS_TRANSACTION_TYPE)
                .value("Bonifico")
                .build();

        final Transaction dto = Transaction.builder()
                .transactionId("TX123")
                .operationId("OP456")
                .accountingDate(LocalDate.of(2025, 10, 1))
                .valueDate(LocalDate.of(2025, 10, 2))
                .type(type)
                .amount(new BigDecimal("123.45"))
                .currency("EUR")
                .description("Pagamento fattura #123")
                .build();

        final Long accountId = 999L;

        /* ***************** ACT ***************** */
        final TransactionEntity entity = transactionMapper.toEntity(dto, accountId);

        /* ***************** ASSERT ***************** */
        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(dto.getTransactionId(), entity.getExternalId());
        assertEquals(dto.getOperationId(), entity.getOperationId());
        assertEquals(dto.getAccountingDate(), entity.getAccountingDate());
        assertEquals(dto.getValueDate(), entity.getValueDate());
        assertEquals(dto.getType().getEnumeration(), entity.getEnumeration());
        assertEquals(dto.getType().getValue(), entity.getValue());
        assertEquals(dto.getAmount(), entity.getAmount());
        assertEquals(dto.getCurrency(), entity.getCurrency());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(accountId, entity.getAccountId());
    }
}
