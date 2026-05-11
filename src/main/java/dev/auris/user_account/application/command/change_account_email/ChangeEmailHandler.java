package dev.auris.user_account.application.command.change_account_email;

import dev.auris.user_account.domain.exception.UserAccountNotFoundException;
import dev.auris.user_account.domain.port.out.LoadUserAccountPort;
import dev.auris.user_account.domain.port.out.SaveUserAccountPort;
import dev.auris.user_account.domain.value_object.Email;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeEmailHandler {
    private final LoadUserAccountPort loadAccountPort;
    private final SaveUserAccountPort saveAccountPort;

    public ChangeEmailHandler(LoadUserAccountPort loadAccountPort, SaveUserAccountPort saveAccountPort) {
        this.loadAccountPort = loadAccountPort;
        this.saveAccountPort = saveAccountPort;
    }

    @Transactional
    public ChangeEmailResponse handle(ChangeEmailCommand command) {
        var userAccount = loadAccountPort.loadById(
                UserAccountId.fromString(command.accountId())
        ).orElseThrow(() -> UserAccountNotFoundException.byId(command.accountId()));

        userAccount.changeEmail(Email.of(command.updatedEmail()));

        var saved = saveAccountPort.save(userAccount);

        return new ChangeEmailResponse(
                saved.getId().getValue().toString(),
                saved.getEmail().getValue()
        );
    }
}
