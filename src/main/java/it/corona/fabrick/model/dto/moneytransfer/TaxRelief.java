package it.corona.fabrick.model.dto.moneytransfer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaxRelief {
    private String taxReliefId;
    @NotNull
    private Boolean isCondoUpgrade;
    @NotBlank
    private String creditorFiscalCode;
    @NotBlank
    private String beneficiaryType;
    @Valid
    private NaturalPersonBeneficiary naturalPersonBeneficiary;
    @Valid
    private LegalPersonBeneficiary legalPersonBeneficiary;
}