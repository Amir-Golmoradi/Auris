package dev.auris.user_account.domain.exception;

import org.springframework.http.HttpStatus;

public final class UserAccountValidationException extends UserAccountException {
    private UserAccountValidationException(String code, String message) {
        super(code, message, HttpStatus.BAD_REQUEST);
    }

    public static UserAccountValidationException invalidEmail(String message) {
        return new UserAccountValidationException("USER_ACCOUNT_INVALID_EMAIL", message);
    }

    public static UserAccountValidationException invalidFullName(String message) {
        return new UserAccountValidationException("USER_ACCOUNT_INVALID_FULL_NAME", message);
    }

    public static UserAccountValidationException invalidPassword(String message) {
        return new UserAccountValidationException("USER_ACCOUNT_INVALID_PASSWORD", message);
    }

    public static UserAccountValidationException invalidPhoneNumber(String message) {
        return new UserAccountValidationException("USER_ACCOUNT_INVALID_PHONE_NUMBER", message);
    }

    public static UserAccountValidationException invalidUsername(String message) {
        return new UserAccountValidationException("USER_ACCOUNT_INVALID_USERNAME", message);
    }

    public static UserAccountValidationException invalidIdentifier(String entityName, String message) {
        return new UserAccountValidationException("USER_ACCOUNT_INVALID_IDENTIFIER", entityName + ": " + message);
    }
}
