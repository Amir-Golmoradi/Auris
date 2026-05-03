package dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.repository;

import dev.auris.user_account.domain.model.UserAccount;
import dev.auris.user_account.domain.port.out.LoadUserAccountPort;
import dev.auris.user_account.domain.port.out.SaveUserAccountPort;
import dev.auris.user_account.domain.value_object.UserAccountId;
import dev.auris.user_account.infrastructure.adapter.out.persistence.mapper.UserAccountAggregateMapper;
import dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.model.UserAccountJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserAccountPersistenceAdapter implements LoadUserAccountPort, SaveUserAccountPort {
    private final UserAccountAggregateMapper mapper;
    private final UserAccountJpaRepository jpaRepository;

    public UserAccountPersistenceAdapter(UserAccountAggregateMapper mapper, UserAccountJpaRepository jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<UserAccount> loadById(UserAccountId userAccountId) {
        return jpaRepository.findById(userAccountId.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<UserAccount> loadByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public List<UserAccount> loadAll() {
        return jpaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        var userEntity = mapper.toEntity(userAccount);
        UserAccountJpaEntity savedEntity = jpaRepository.save(userEntity);
        return mapper.toDomain(savedEntity);
    }
}
