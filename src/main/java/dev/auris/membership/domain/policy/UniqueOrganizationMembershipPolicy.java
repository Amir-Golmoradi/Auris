package dev.auris.membership.domain.policy;

import dev.auris.membership.domain.exception.MembershipStateException;
import dev.auris.membership.domain.port.out.LoadMembershipPort;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;

public class UniqueOrganizationMembershipPolicy {
    private final OrganizationId targetOrganizationId;

    public UniqueOrganizationMembershipPolicy(OrganizationId targetOrgId) {
        this.targetOrganizationId = targetOrgId;
    }

    public void enforce(UserAccountId userAccountId, LoadMembershipPort port) {
        if (!isSatisfiedBy(userAccountId, port)) {
            throw MembershipStateException.alreadyMember(userAccountId, targetOrganizationId);
        }
    }

    /**
     * Returns true if the user is NOT already a member —
     * meaning the action is permitted.
     */
    public boolean isSatisfiedBy(UserAccountId userAccountId, LoadMembershipPort port) {
        return port.loadByUserAndOrganization(userAccountId, targetOrganizationId).isEmpty();
    }
}
