package it.corona.fabrick.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BeneficiaryType {
    NATURAL_PERSON("NATURAL_PERSON", "If the beneficiary is a natural person"),
    LEGAL_PERSON("LEGAL_PERSON", "If the beneficiary is a legal person");

    private final String code;
    private final String description;
}
