package dev.auris.user_account.application.command.change_account_name;

public record ChangeFullNameResponse(
        String accountId,
        String firstName,
        String lastName
) {
}
