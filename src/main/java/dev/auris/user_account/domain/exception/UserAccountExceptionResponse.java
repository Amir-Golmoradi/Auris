package dev.auris.user_account.domain.exception;

import java.time.Instant;

public record UserAccountExceptionResponse(
        Instant timestamp,
        int status,
        String code,
        String message
) {
    public static UserAccountExceptionResponse of(UserAccountException exception, int status) {
        return new UserAccountExceptionResponse(
                Instant.now(),
                status,
                exception.getCode(),
                exception.getMessage()
        );
    }
}
