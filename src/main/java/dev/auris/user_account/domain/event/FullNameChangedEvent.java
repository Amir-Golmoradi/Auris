package dev.auris.user_account.domain.event;

import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.FullName;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record FullNameChangedEvent(
        UserAccountId userAccountId,
        FullName updatedFullName,
        OffsetDateTime publishedOn,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public FullNameChangedEvent(UserAccountId accountId, FullName updatedFullName, OffsetDateTime publishedOn) {
        this(accountId, updatedFullName, publishedOn, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
