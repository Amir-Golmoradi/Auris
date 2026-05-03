package dev.auris.membership.domain.event;

import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record MemberPromotedToOwnerEvent(
        MembershipId membershipId,
        UserAccountId userAccountId,
        OrganizationId organizationId,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public MemberPromotedToOwnerEvent(MembershipId id, UserAccountId ua, OrganizationId org) {
        this(id, ua, org, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
