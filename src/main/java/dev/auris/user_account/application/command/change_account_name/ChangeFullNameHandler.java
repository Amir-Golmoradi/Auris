package dev.auris.user_account.application.command.change_account_name;

import dev.auris.user_account.domain.exception.UserAccountNotFoundException;
import dev.auris.user_account.domain.port.out.LoadUserAccountPort;
import dev.auris.user_account.domain.port.out.SaveUserAccountPort;
import dev.auris.user_account.domain.value_object.FullName;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeFullNameHandler {
    private final LoadUserAccountPort loadAccountPort;
    private final SaveUserAccountPort saveUserAccountPort;

    public ChangeFullNameHandler(LoadUserAccountPort loadAccountPort, SaveUserAccountPort saveUserAccountPort) {
        this.loadAccountPort = loadAccountPort;
        this.saveUserAccountPort = saveUserAccountPort;
    }

    @Transactional
    public ChangeFullNameResponse handle(ChangeFullNameCommand command) {
        var userAccount = loadAccountPort.loadById(
                UserAccountId.fromString(command.accountId())
        ).orElseThrow(() -> UserAccountNotFoundException.byId(command.accountId()));

        userAccount.changeFullName(FullName.of(command.firstName(), command.lastName()));

        var saved = saveUserAccountPort.save(userAccount);

        return new ChangeFullNameResponse(
                saved.getId().getValue().toString(),
                saved.getFullName().getFirstName(),
                saved.getFullName().getLastName()
        );
    }
}
