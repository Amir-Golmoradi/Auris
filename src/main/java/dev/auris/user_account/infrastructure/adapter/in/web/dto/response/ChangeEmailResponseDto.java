package dev.auris.user_account.infrastructure.adapter.in.web.dto.response;

public record ChangeEmailResponseDto(
        String accountId,
        String updatedEmail
) {
}
