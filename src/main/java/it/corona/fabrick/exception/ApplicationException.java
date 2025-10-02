package it.corona.fabrick.exception;

import it.corona.fabrick.enums.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public ApplicationException(List<ApiError> errors) {
        super(errors.stream().map(ApiError::getDescription).toList().toString());
        this.errorResponse = buildErrorResponse(errors);
    }

    public ApplicationException(ApiError error) {
        super(error.getDescription());
        this.errorResponse = buildErrorResponse(error);
    }

    public ApplicationException(ApiError error, String message) {
        super(message);
        this.errorResponse = buildErrorResponse(error);
    }

    public ApplicationException(ApiError error, Throwable cause) {
        super(error.getDescription(), cause);
        this.errorResponse = buildErrorResponse(error);
    }

    public ApplicationException(ApiError error, String message, Throwable cause) {
        super(message, cause);
        this.errorResponse = buildErrorResponse(error);
    }

    private static ErrorResponse buildErrorResponse(ApiError error) {
        return new ErrorResponse(List.of(new ErrorDetail(error)));
    }

    private static ErrorResponse buildErrorResponse(List<ApiError> errors) {
        return new ErrorResponse(errors.stream().map(ErrorDetail::new).toList());
    }
}
