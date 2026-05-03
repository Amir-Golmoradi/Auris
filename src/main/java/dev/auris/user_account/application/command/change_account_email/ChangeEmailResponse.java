package dev.auris.user_account.application.command.change_account_email;

public record ChangeEmailResponse(
        String accountId,
        String updatedEmail
) {
}
