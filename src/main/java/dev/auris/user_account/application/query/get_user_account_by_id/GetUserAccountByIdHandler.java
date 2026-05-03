package dev.auris.user_account.application.query.get_user_account_by_id;

import dev.auris.user_account.domain.exception.UserAccountNotFoundException;
import dev.auris.user_account.domain.port.out.LoadUserAccountPort;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;

@Service
public class GetUserAccountByIdHandler {
    private final LoadUserAccountPort loadUserAccountPort;

    public GetUserAccountByIdHandler(LoadUserAccountPort loadUserAccountPort) {
        this.loadUserAccountPort = loadUserAccountPort;
    }

    public GetUserAccountByIdResponse handle(GetUserAccountByIdQuery query) {
        var userAccount = loadUserAccountPort.loadById(
                UserAccountId.fromString(query.userAccountId())
        ).orElseThrow(() -> UserAccountNotFoundException.byId(query.userAccountId()));

        return new GetUserAccountByIdResponse(
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
