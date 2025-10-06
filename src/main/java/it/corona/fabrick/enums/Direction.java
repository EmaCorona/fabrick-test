package it.corona.fabrick.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction {
    INCOMING("INCOMING", "Incoming money transfer"),
    OUTGOING("OUTGOING", "Outgoing money transfer");

    private final String code;
    private final String description;
}
