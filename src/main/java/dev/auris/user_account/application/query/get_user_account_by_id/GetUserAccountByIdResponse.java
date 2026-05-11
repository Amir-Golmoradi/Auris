package dev.auris.user_account.application.query.get_user_account_by_id;

import dev.auris.user_account.domain.enums.UserStatus;

import java.time.OffsetDateTime;

public record GetUserAccountByIdResponse(
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
