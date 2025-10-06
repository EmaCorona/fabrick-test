package it.corona.fabrick.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeeType {
    SHA("SHA", "Shared fees (typical value to be used for SEPA transfers)"),
    OUR("OUR", "Fees apply to debtor (valid only if the creditor account is rooted on a non-SEPA bank)"),
    BEN("BEN", "Fees apply to creditor (valid only if the creditor account is rooted on a non-SEPA bank)");

    private final String code;
    private final String description;
}
