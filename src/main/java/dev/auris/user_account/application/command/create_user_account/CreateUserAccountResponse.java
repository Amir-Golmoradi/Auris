package dev.auris.user_account.application.command.create_user_account;

import dev.auris.user_account.domain.enums.UserStatus;
import dev.auris.user_account.domain.model.UserAccount;

/**
 * Application-layer DTO returned by {@link CreateUserAccountHandler}.
 * Carries only the data the caller needs — keeps the domain aggregate
 * from leaking across the application boundary.
 */
public record CreateUserAccountResponse(
        String userAccountId,
        String email,
        String username,
        String firstName,
        String lastName,
        String phoneNumber,
        String description,
        UserStatus status
) {
    public static CreateUserAccountResponse from(UserAccount userAccount) {
        return new CreateUserAccountResponse(
                userAccount.getId().getValue().toString(),
                userAccount.getEmail().getValue(),
                userAccount.getUsername().getValue(),
                userAccount.getFullName().getFirstName(),
                userAccount.getFullName().getLastName(),
                userAccount.getPhoneNumber().getValue(),
                userAccount.getDescription(),
                userAccount.getStatus()
        );
    }
}
