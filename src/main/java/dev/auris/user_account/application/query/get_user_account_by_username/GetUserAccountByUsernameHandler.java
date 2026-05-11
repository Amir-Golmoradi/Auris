package dev.auris.user_account.application.query.get_user_account_by_username;

import dev.auris.user_account.domain.exception.UserAccountNotFoundException;
import dev.auris.user_account.domain.port.out.LoadUserAccountPort;
import org.springframework.stereotype.Service;

@Service
public class GetUserAccountByUsernameHandler {
    private final LoadUserAccountPort loadUserAccountPort;

    public GetUserAccountByUsernameHandler(LoadUserAccountPort loadUserAccountPort) {
        this.loadUserAccountPort = loadUserAccountPort;
    }

    public GetUserAccountByUsernameResponse handle(GetUserAccountByUsernameQuery query) {
        var userAccount = loadUserAccountPort.loadByUsername(query.username())
                .orElseThrow(() -> UserAccountNotFoundException.byUsername(query.username()));

        return new GetUserAccountByUsernameResponse(
                userAccount.getId().getValue().toString(),
                userAccount.getEmail().getValue(),
                userAccount.getUsername().getValue(),
                userAccount.getFullName().getFirstName(),
                userAccount.getFullName().getLastName(),
                userAccount.getPhoneNumber().getValue(),
                userAccount.getDescription(),
                userAccount.getStatus(),
                userAccount.getCreatedAt()
        );
    }
}
