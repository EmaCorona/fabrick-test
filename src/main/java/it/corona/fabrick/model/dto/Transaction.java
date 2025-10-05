package it.corona.fabrick.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String transactionId;
    private String operationId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate accountingDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate valueDate;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String description;
}
