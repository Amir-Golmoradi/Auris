package dev.auris.membership.application.command.suspend_membership_command;

import jakarta.validation.constraints.NotBlank;

public record SuspendMembershipCommand(
        @NotBlank(message = "Actor user account ID is required")
        String actorUserAccountId,

        @NotBlank(message = "Target user account ID is required")
        String targetUserAccountId,

        @NotBlank(message = "Organization ID is required")
        String organizationId
) {
}
