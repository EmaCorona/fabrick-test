package it.corona.fabrick.model.request;

import it.corona.fabrick.enums.FeeType;
import it.corona.fabrick.model.dto.moneytransfer.Creditor;
import it.corona.fabrick.model.dto.moneytransfer.TaxRelief;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PaymentRequest {
    @NotNull
    @Valid
    private Creditor creditor;
    private LocalDate executionDate;
    private String uri;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String currency;
    private Boolean isUrgent;
    private Boolean isInstant;
    private FeeType feeType;
    private String feeAccountId;
    @Valid
    private TaxRelief taxRelief;
}