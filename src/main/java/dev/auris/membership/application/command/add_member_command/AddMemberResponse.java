package dev.auris.membership.application.command.add_member_command;

import dev.auris.membership.domain.model.Membership;

public record AddMemberResponse(
        String membershipId,
        String userAccountId,
        String organizationId,
        String role,
        String status
) {
    public static AddMemberResponse from(Membership membership) {
        return new AddMemberResponse(
                membership.getId().getValue().toString(),
                membership.getUserAccountId().getValue().toString(),
                membership.getOrganizationId().getValue().toString(),
                membership.getRole().name(),
                membership.getStatus().name()
        );
    }
}

