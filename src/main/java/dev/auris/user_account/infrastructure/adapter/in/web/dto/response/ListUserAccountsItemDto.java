package dev.auris.user_account.infrastructure.adapter.in.web.dto.response;

import dev.auris.user_account.domain.enums.UserStatus;

import java.time.OffsetDateTime;

public record ListUserAccountsItemDto(
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
