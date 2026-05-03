package dev.auris.user_account.application.query.list_user_accounts;

import dev.auris.user_account.domain.port.out.LoadUserAccountPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUserAccountsHandler {
    private final LoadUserAccountPort loadUserAccountPort;

    public ListUserAccountsHandler(LoadUserAccountPort loadUserAccountPort) {
        this.loadUserAccountPort = loadUserAccountPort;
    }

    public List<ListUserAccountsItem> handle(ListUserAccountsQuery query) {
        return loadUserAccountPort.loadAll().stream()
                .map(userAccount -> new ListUserAccountsItem(
                        userAccount.getId().getValue().toString(),
                        userAccount.getEmail().getValue(),
                        userAccount.getUsername().getValue(),
                        userAccount.getFullName().getFirstName(),
                        userAccount.getFullName().getLastName(),
                        userAccount.getPhoneNumber().getValue(),
                        userAccount.getDescription(),
                        userAccount.getStatus(),
                        userAccount.getCreatedAt()
                ))
                .toList();
    }
}
