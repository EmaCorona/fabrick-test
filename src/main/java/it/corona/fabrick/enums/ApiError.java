package it.corona.fabrick.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiError {

    /* ********** HTTP ERRORS **********/
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "Internal server error"),
    EXTERNAL_CALL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "EXTERNAL_CALL_ERROR", "An error occurred while making a call to an external service"),
    BANK_ACCOUNT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BANKING_ACCOUNT_CASH_ERROR", "Error while retrieving bank account"),
    BANK_ACCOUNT_BALANCE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BANKING_ACCOUNT_BALANCE_ERROR", "Error while retrieving account balance");

    private final HttpStatus httpStatus;
    private final String code;
    private final String description;
}
