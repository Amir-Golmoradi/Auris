package dev.auris.user_account.application.query.get_user_account_profile;

import dev.auris.user_account.domain.exception.UserAccountNotFoundException;
import dev.auris.user_account.domain.port.out.LoadUserAccountPort;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;

@Service
public class GetUserAccountProfileHandler {
    private final LoadUserAccountPort loadUserAccountPort;

    public GetUserAccountProfileHandler(LoadUserAccountPort loadUserAccountPort) {
        this.loadUserAccountPort = loadUserAccountPort;
    }

    public GetUserAccountProfileResponse handle(GetUserAccountProfileQuery query) {
        var userAccount = loadUserAccountPort.loadById(
                UserAccountId.fromString(query.userAccountId())
        ).orElseThrow(() -> UserAccountNotFoundException.byId(query.userAccountId()));

        return new GetUserAccountProfileResponse(
                userAccount.getId().getValue().toString(),
                userAccount.getUsername().getValue(),
                userAccount.getEmail().getValue(),
                userAccount.getFullName().getFirstName(),
                userAccount.getFullName().getLastName(),
                userAccount.getPhoneNumber().getValue(),
                userAccount.getDescription()
        );
    }
}
