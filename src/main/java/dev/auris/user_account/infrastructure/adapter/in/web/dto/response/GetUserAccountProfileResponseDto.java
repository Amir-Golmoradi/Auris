package dev.auris.user_account.infrastructure.adapter.in.web.dto.response;

public record GetUserAccountProfileResponseDto(
        String userAccountId,
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String description
) {
}
