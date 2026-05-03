package dev.auris.membership.application.command.suspend_membership_command;

import dev.auris.membership.domain.model.Membership;

import java.time.OffsetDateTime;

public record SuspendMembershipResponse(
        String membershipId,
        String userAccountId,
        String organizationId,
        OffsetDateTime suspendedAt
) {
    public static SuspendMembershipResponse from(Membership membership) {
        return new SuspendMembershipResponse(
                membership.getId().getValue().toString(),
                membership.getUserAccountId().getValue().toString(),
                membership.getOrganizationId().getValue().toString(),
                membership.getUpdatedAt()
        );
    }
}

