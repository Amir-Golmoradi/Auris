package dev.auris.membership.application.command.promote_member_to_owner_account;

import dev.auris.membership.domain.model.Membership;

import java.time.OffsetDateTime;

public record PromoteMemberToOwnerResponse(
        String membershipId,
        String userAccountId,
        String organizationId,
        String role,
        OffsetDateTime updatedAt
) {
    public static PromoteMemberToOwnerResponse from(Membership membership) {
        return new PromoteMemberToOwnerResponse(
                membership.getId().getValue().toString(),
                membership.getUserAccountId().getValue().toString(),
                membership.getOrganizationId().getValue().toString(),
                membership.getRole().name(),
                membership.getUpdatedAt()
        );
    }
}
