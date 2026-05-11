package dev.auris.identity.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TransitKeyValidationException extends RuntimeException {
    public TransitKeyValidationException(String message) {
        super(message);
    }

    public TransitKeyValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
