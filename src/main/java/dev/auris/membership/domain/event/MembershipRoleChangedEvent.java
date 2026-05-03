package dev.auris.membership.domain.event;

import dev.auris.membership.domain.enums.MembershipRole;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record MembershipRoleChangedEvent(
        MembershipId membershipId,
        UserAccountId userAccountId,
        MembershipRole newRole,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public MembershipRoleChangedEvent(MembershipId id, UserAccountId ua, MembershipRole role) {
        this(id, ua, role, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
