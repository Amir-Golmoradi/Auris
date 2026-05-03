package dev.auris.identity.infrastructure.authentication;

import dev.auris.membership.domain.enums.MembershipRole;
import dev.auris.user_account.domain.enums.UserStatus;
import dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.model.UserAccountJpaEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private final UserAccountJpaEntity user;
    private final Set<MembershipRole> membershipRoles;

    public SecurityUser(UserAccountJpaEntity user, Set<MembershipRole> membershipRoles) {
        this.user = user;
        this.membershipRoles = membershipRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return membershipRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus() != null
                && user.getStatus() != UserStatus.NOT_ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == UserStatus.ACTIVE;
    }
}
