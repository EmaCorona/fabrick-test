package it.corona.fabrick.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(ApplicationException ex) {
        return getErrorResponse(ex);
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(ApplicationException ex) {
        String errors = ex.getErrorResponse().getErrors().stream().map(ErrorDetail::getDescription).toList().toString();
        log.error("ERRORS: {}", errors);
        ErrorResponse response = new ErrorResponse(ex.getErrorResponse().getErrors());
        return ResponseEntity.status(response.getErrors().getFirst().getStatus()).body(response);
    }
}