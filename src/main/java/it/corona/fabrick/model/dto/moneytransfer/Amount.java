package it.corona.fabrick.model.dto.moneytransfer;

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
public class Amount {
    private BigDecimal debtorAmount;
    private String debtorCurrency;
    private BigDecimal creditorAmount;
    private String creditorCurrency;
    private LocalDate creditorCurrencyDate;
    private Long exchangeRate;
}
