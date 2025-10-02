package it.corona.fabrick.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FabrickHeader {
    APY_KEY("apikey"),
    AUTH_SCHEMA("Auth-Schema");

    private final String value;
}
