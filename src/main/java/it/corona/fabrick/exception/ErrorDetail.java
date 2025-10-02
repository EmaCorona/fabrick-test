package it.corona.fabrick.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.corona.fabrick.enums.ApiError;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorDetail {
    private Integer status;
    private String code;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorDetail(ApiError error) {
        this.status = error.getHttpStatus().value();
        this.code = error.getCode();
        this.description = error.getDescription();
    }

    public ErrorDetail(ApiError error, String message) {
        this.status = error.getHttpStatus().value();
        this.code = error.getCode();
        this.description = message;
    }

    public ErrorDetail(int status, String codice, String description) {
        this.status = status;
        this.code = codice;
        this.description = description;
    }
}
