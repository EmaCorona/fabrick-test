package it.corona.fabrick.model.dto.moneytransfer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LegalPersonBeneficiary {
    @NotNull
    private String fiscalCode;
    private String legalRepresentativeFiscalCode;
}
