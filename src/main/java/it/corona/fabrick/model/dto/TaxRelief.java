package it.corona.fabrick.model.dto;

import it.corona.fabrick.enums.BeneficiaryType;
import it.corona.fabrick.enums.TaxReliefId;
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
    private TaxReliefId taxReliefId;
    @NotNull
    private Boolean isCondoUpgrade;
    @NotBlank
    private String creditorFiscalCode;
    @NotNull
    private BeneficiaryType beneficiaryType;
    @Valid
    private NaturalPersonBeneficiary naturalPersonBeneficiary;
    @Valid
    private LegalPersonBeneficiary legalPersonBeneficiary;
}