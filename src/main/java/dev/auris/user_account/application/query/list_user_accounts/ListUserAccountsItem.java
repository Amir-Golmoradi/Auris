package dev.auris.user_account.application.query.list_user_accounts;

import dev.auris.user_account.domain.enums.UserStatus;

import java.time.OffsetDateTime;

public record ListUserAccountsItem(
        String userAccountId,
        String email,
        String username,
        String firstName,
        String lastName,
        String phoneNumber,
        String description,
        UserStatus status,
        OffsetDateTime createdAt
) {
}
