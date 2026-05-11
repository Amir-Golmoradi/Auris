package dev.auris.user_account.application.command.register_new_owner_account;

public record RegisterNewOwnerResponse(
        String accountId,
        String organizationId,
        String email,
        String username,
        String firstName,
        String lastName,
        String phoneNumber,
        String organizationName,
        String officePhoneNumber,
        String description
) {
}
