package it.corona.fabrick.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoneyTransferStatus {
    EXECUTED("EXECUTED", "The money transfer has been executed"),
    BOOKED("BOOKED", "The money transfer has been booked for execution on execution date"),
    WORK_IN_PROGRESS("WORK_IN_PROGRESS", "The money transfer execution is in progress"),
    CANCELLED("CANCELLED", "The money transfer has been cancelled by the customer (applies only to formerly booked money transfers)"),
    REJECTED("REJECTED", "The money transfer has been rejected");

    private final String code;
    private final String description;
}
