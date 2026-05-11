package dev.auris.membership.domain.event;

import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record MembershipReactivatedEvent(
        MembershipId membershipId,
        UserAccountId userAccountId,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public MembershipReactivatedEvent(MembershipId id, UserAccountId ua) {
        this(id, ua, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
