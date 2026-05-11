package dev.auris.user_account.domain.exception;

import org.springframework.http.HttpStatus;

public final class UserAccountAuthorizationException extends UserAccountException {
    private UserAccountAuthorizationException(String code, String message) {
        super(code, message, HttpStatus.FORBIDDEN);
    }

    public static UserAccountAuthorizationException unauthorizedRole(String userAccountId, String requiredRole) {
        return new UserAccountAuthorizationException(
                "USER_ACCOUNT_UNAUTHORIZED_ROLE",
                STR."User account '\{userAccountId}' does not have the required membershipRole: \{requiredRole}"
        );
    }
}
