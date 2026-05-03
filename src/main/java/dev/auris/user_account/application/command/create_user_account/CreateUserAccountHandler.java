package dev.auris.user_account.application.command.create_user_account;

import dev.auris.organization.domain.service.PasswordHasher;
import dev.auris.user_account.domain.model.UserAccount;
import dev.auris.user_account.domain.port.out.SaveUserAccountPort;
import dev.auris.user_account.domain.value_object.Email;
import dev.auris.user_account.domain.value_object.FullName;
import dev.auris.user_account.domain.value_object.Password;
import dev.auris.user_account.domain.value_object.PhoneNumber;
import dev.auris.user_account.domain.value_object.UserName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command used to create and persist a new owner user account.
 * The handler returns {@code CreateUserAccountCommandResponse} so callers get
 * the use-case result without leaking the domain aggregate across the
 * application boundary.
 */

@Service
public class CreateUserAccountHandler {
    private final PasswordHasher passwordHasher;
    private final SaveUserAccountPort saveUserAccountPort;

    public CreateUserAccountHandler(PasswordHasher passwordHasher, SaveUserAccountPort saveUserAccountPort) {
        this.passwordHasher = passwordHasher;
        this.saveUserAccountPort = saveUserAccountPort;
    }

    @Transactional
    public CreateUserAccountResponse handle(CreateUserAccountCommand command) {
        var userAccount = UserAccount.registerNewOwner(
                Email.of(command.email()),
                Password.of(command.password(), passwordHasher),
                UserName.of(command.username()),
                FullName.of(command.firstName(), command.lastName()),
                PhoneNumber.of(command.phoneNumber()),
                command.description()
        );
        var saved = saveUserAccountPort.save(userAccount);
        return CreateUserAccountResponse.from(saved);
    }
}
