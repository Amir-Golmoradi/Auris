package dev.auris.user_account.domain.port.out;

import dev.auris.user_account.domain.model.UserAccount;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface LoadUserAccountPort {
    Optional<UserAccount> loadById(UserAccountId userAccountId);
    Optional<UserAccount> loadByUsername(String username);
    List<UserAccount> loadAll();
}
