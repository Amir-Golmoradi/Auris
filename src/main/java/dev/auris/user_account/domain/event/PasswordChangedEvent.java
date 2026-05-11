package dev.auris.user_account.domain.event;

import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record PasswordChangedEvent(
        UserAccountId userAccountId,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public PasswordChangedEvent(UserAccountId accountId) {
        this(accountId, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
