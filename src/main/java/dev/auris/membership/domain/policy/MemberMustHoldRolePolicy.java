package dev.auris.membership.domain.policy;

import dev.auris.membership.domain.enums.MembershipRole;
import dev.auris.membership.domain.model.Membership;
import dev.auris.user_account.domain.exception.UserAccountAuthorizationException;

public class MemberMustHoldRolePolicy {
    private final MembershipRole requiredMembershipRole;

    public MemberMustHoldRolePolicy(MembershipRole requiredMembershipRole) {
        this.requiredMembershipRole = requiredMembershipRole;
    }

    public void enforce(Membership user) {
        if (!isSatisfiedBy(user)) {
            throw UserAccountAuthorizationException.unauthorizedRole(
                    String.valueOf(user.getId().getValue()), requiredMembershipRole.name()
            );
        }
    }

    public boolean isSatisfiedBy(Membership member) {
        return member.getRole() == requiredMembershipRole;
    }
}
