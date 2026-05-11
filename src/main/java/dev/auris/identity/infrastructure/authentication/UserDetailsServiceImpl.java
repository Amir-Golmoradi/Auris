package dev.auris.identity.infrastructure.authentication;

import dev.auris.membership.domain.port.out.LoadMembershipPort;
import dev.auris.user_account.domain.value_object.UserAccountId;
import dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.model.UserAccountJpaEntity;
import dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.repository.UserAccountJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccountJpaRepository jpaRepository;
    private final LoadMembershipPort loadMembershipPort;

    public UserDetailsServiceImpl(UserAccountJpaRepository jpaRepository, LoadMembershipPort loadMembershipPort) {
        this.jpaRepository = jpaRepository;
        this.loadMembershipPort = loadMembershipPort;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountJpaEntity user = jpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var membershipRoles = loadMembershipPort
                .loadAllActiveByUserId(UserAccountId.fromString(user.getId().toString()))
                .stream()
                .map(membership -> membership.getRole())
                .collect(java.util.stream.Collectors.toSet());

        return new SecurityUser(user, membershipRoles);
    }
}
