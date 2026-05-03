package dev.auris.membership.domain.event;

import dev.auris.membership.domain.enums.SuspensionReason;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.shared.building_block.DomainEvent;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record MembershipSuspendedEvent(
        MembershipId membershipId,
        SuspensionReason reason,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public MembershipSuspendedEvent(MembershipId id, SuspensionReason reason) {
        this(id, reason, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
