package dev.auris.membership.application.command.promote_member_to_owner_account;

import jakarta.validation.constraints.NotBlank;

public record PromoteMemberToOwnerCommand(
        @NotBlank(message = "Actor user account ID is required")
        String actorUserAccountId,

        @NotBlank(message = "Target user account ID is required")
        String targetUserAccountId,

        @NotBlank(message = "Organization ID is required")
        String organizationId
) {
}


