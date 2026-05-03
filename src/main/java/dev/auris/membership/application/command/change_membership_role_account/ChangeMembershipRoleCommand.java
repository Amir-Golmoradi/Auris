package dev.auris.membership.application.command.change_membership_role_account;

import dev.auris.membership.domain.enums.MembershipRole;
import jakarta.validation.constraints.NotBlank;

import java.util.Arrays;

public record ChangeMembershipRoleCommand(
        @NotBlank(message = "Actor user account ID is required")
        String actorUserAccountId,

        @NotBlank(message = "Target user account ID is required")
        String targetUserAccountId,

        @NotBlank(message = "Organization ID is required")
        String organizationId,

        @NotBlank(message = "New role is required")
        String newRole
) {
    public ChangeMembershipRoleCommand {
        try {
            MembershipRole.valueOf(newRole.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Invalid role [%s]. Must be one of: %s"
                            .formatted(newRole, Arrays.toString(MembershipRole.values()))
            );
        }
    }

    public MembershipRole parsedRole() {
        return MembershipRole.valueOf(newRole.toUpperCase());
    }

}