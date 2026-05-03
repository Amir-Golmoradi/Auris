package dev.auris.user_account.domain.port.out;

import dev.auris.user_account.domain.model.UserAccount;

public interface SaveUserAccountPort {
    UserAccount save(UserAccount userAccount);
}