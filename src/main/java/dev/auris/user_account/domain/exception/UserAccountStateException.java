package dev.auris.user_account.domain.exception;

import org.springframework.http.HttpStatus;

public final class UserAccountStateException extends UserAccountException {
    private UserAccountStateException(String code, String message) {
        super(code, message, HttpStatus.CONFLICT);
    }

    public static UserAccountStateException alreadyActive(String userAccountId) {
        return new UserAccountStateException(
                "USER_ACCOUNT_ALREADY_ACTIVE",
                STR."User account is already active: \{userAccountId}"
        );
    }

    public static UserAccountStateException notActive(String userAccountId) {
        return new UserAccountStateException(
                "USER_ACCOUNT_NOT_ACTIVE",
                STR."User account is not active: \{userAccountId}"
        );
    }

    public static UserAccountStateException alreadyMemberOfOrganization(String userAccountId, String organizationId) {
        return new UserAccountStateException(
                "USER_ACCOUNT_ALREADY_MEMBER_OF_ORGANIZATION",
                STR."User account '\{userAccountId}' is already a member of organization '\{organizationId}'"
        );
    }
}
