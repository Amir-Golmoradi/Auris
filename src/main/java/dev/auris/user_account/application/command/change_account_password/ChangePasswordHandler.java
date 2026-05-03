package dev.auris.user_account.application.command.change_account_password;

import dev.auris.organization.domain.service.PasswordHasher;
import dev.auris.user_account.domain.exception.UserAccountNotFoundException;
import dev.auris.user_account.domain.model.UserAccount;
import dev.auris.user_account.domain.port.out.LoadUserAccountPort;
import dev.auris.user_account.domain.port.out.SaveUserAccountPort;
import dev.auris.user_account.domain.value_object.Password;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangePasswordHandler {
    private final PasswordHasher passwordHasher;
    private final LoadUserAccountPort loadAccount;
    private final SaveUserAccountPort saveAccount;

    public ChangePasswordHandler(PasswordHasher passwordHasher, LoadUserAccountPort loadAccount, SaveUserAccountPort saveAccount) {
        this.passwordHasher = passwordHasher;
        this.loadAccount = loadAccount;
        this.saveAccount = saveAccount;
    }

    @Transactional
    public ChangePasswordResponse handle(ChangePasswordCommand command) {
        UserAccount userAccount = loadAccount.loadById(UserAccountId.fromString(command.userAccountId()))
                .orElseThrow(() -> UserAccountNotFoundException.byId(command.userAccountId()));

        userAccount.changePassword(Password.of(command.newPassword(), passwordHasher));
        UserAccount saved = saveAccount.save(userAccount);

        return new ChangePasswordResponse(saved.getId().getValue().toString());
    }

}
