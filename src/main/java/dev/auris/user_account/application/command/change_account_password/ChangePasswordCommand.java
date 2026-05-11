package dev.auris.user_account.application.command.change_account_password;

public record ChangePasswordCommand(
    String userAccountId,
    String newPassword
) {
}
