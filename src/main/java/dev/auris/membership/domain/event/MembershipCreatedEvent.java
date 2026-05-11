package dev.auris.membership.domain.event;

import dev.auris.membership.domain.enums.MembershipRole;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record MembershipCreatedEvent(
        MembershipId membershipId,
        UserAccountId userAccountId,
        OrganizationId organizationId,
        MembershipRole role,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public MembershipCreatedEvent(MembershipId membershipId, UserAccountId userAccountId, OrganizationId organizationId, MembershipRole role) {
        this(membershipId, userAccountId, organizationId, role, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
