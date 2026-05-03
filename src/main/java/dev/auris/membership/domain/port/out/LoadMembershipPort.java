package dev.auris.membership.domain.port.out;

import dev.auris.membership.domain.model.Membership;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface LoadMembershipPort {
    Optional<Membership> loadById(MembershipId membershipId);
    List<Membership> loadAllActiveByUserId(UserAccountId userAccountId);
    List<Membership> loadAllByOrganizationId(OrganizationId organizationId);
    Optional<Membership> loadByUserAndOrganization(UserAccountId userAccountId, OrganizationId organizationId);
}
