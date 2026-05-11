package dev.auris.organization.domain.event;

import dev.auris.organization.domain.enums.InvitationCodeType;
import dev.auris.organization.domain.value_object.InvitationCode;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.shared.building_block.DomainEvent;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record MemberInvitationCodeAssignedEvent(
        OrganizationId organizationId,
        InvitationCode invitationCode,
        OffsetDateTime occurredOn
) implements DomainEvent {
    public MemberInvitationCodeAssignedEvent(OrganizationId organizationId, InvitationCode invitationCode) {
        this(organizationId, invitationCode, OffsetDateTime.now(ZoneOffset.UTC));
    }

    @Override
    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }

    public InvitationCodeType type() {
        return InvitationCodeType.MEMBER;
    }
}
