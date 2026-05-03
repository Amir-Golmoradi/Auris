package dev.auris.user_account.domain.exception;

import org.springframework.http.HttpStatus;

public final class UserAccountNotFoundException extends UserAccountException {
    private UserAccountNotFoundException(String code, String message) {
        super(code, message, HttpStatus.NOT_FOUND);
    }

    public static UserAccountNotFoundException byId(String userAccountId) {
        return new UserAccountNotFoundException(
                "USER_ACCOUNT_NOT_FOUND",
                STR."User account not found: \{userAccountId}"
        );
    }

    public static UserAccountNotFoundException byUsername(String username) {
        return new UserAccountNotFoundException(
                "USER_ACCOUNT_NOT_FOUND",
                STR."User account not found: \{username}"
        );
    }
}
