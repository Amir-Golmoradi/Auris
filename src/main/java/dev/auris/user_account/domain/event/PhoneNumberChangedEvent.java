package dev.auris.user_account.domain.event;

import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.PhoneNumber;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record PhoneNumberChangedEvent(
        UserAccountId userAccountId,
        PhoneNumber updatedPhoneNumber,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public PhoneNumberChangedEvent(UserAccountId accountId, PhoneNumber updatedPhoneNumber) {
        this(accountId, updatedPhoneNumber, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
