package dev.auris.user_account.application.query.get_user_account_by_username;

import dev.auris.user_account.domain.enums.UserStatus;

import java.time.OffsetDateTime;

public record GetUserAccountByUsernameResponse(
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
