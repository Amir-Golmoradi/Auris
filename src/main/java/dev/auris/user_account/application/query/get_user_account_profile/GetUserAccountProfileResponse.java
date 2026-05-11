package dev.auris.user_account.application.query.get_user_account_profile;

public record GetUserAccountProfileResponse(
        String userAccountId,
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String description
) {
}
