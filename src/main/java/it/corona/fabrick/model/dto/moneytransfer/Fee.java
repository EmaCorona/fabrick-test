package it.corona.fabrick.model.dto.moneytransfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fee {
    private String feeCode;
    private String description;
    private BigDecimal amount;
    private String currency;
}
