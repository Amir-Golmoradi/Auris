package dev.auris.user_account.infrastructure.adapter.in.web.dto.response;

public record ChangeFullNameResponseDto(
        String accountId,
        String firstName,
        String lastName
) {
}
