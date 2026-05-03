package dev.auris.user_account.domain.event;

import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.Email;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record EmailChangedEvent(
        UserAccountId userAccountId,
        Email updatedEmail,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public EmailChangedEvent(UserAccountId accountId, Email updatedEmail){
        this(accountId, updatedEmail, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}
