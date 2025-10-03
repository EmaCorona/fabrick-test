package it.corona.fabrick.exception;

import it.corona.fabrick.enums.ApiError;
import it.corona.fabrick.model.dto.FabrickError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public ApplicationException(ApiError error) {
        super(error.getDescription());
        this.errorResponse = buildErrorResponse(error);
    }

    public ApplicationException(List<FabrickError> errors) {
        List<ErrorDetail> errorDetails = errors.stream().map(ApplicationException::buildErrorDetail).toList();
        this.errorResponse = new ErrorResponse(errorDetails);
    }

    public ApplicationException(ApiError error, String message) {
        super(message);
        this.errorResponse = buildErrorResponse(error, message);
    }

    public ApplicationException(ApiError error, Throwable cause) {
        super(error.getDescription(), cause);
        this.errorResponse = buildErrorResponse(error, cause.getMessage());
    }

    public ApplicationException(ApiError error, String message, Throwable cause) {
        super(message, cause);
        this.errorResponse = buildErrorResponse(error, message);
    }

    private ErrorResponse buildErrorResponse(ApiError error) {
        return new ErrorResponse(List.of(new ErrorDetail(error)));
    }

    private ErrorResponse buildErrorResponse(ApiError error, String message) {
        return new ErrorResponse(List.of(new ErrorDetail(error, message)));
    }

    private static ErrorDetail buildErrorDetail(FabrickError error) {
        return new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getCode(), error.getDescription());
    }
}
