package dev.auris.organization.application.command.generate_member_invitation_code;

import jakarta.validation.constraints.NotBlank;

public record GenerateMemberInvitationCodeCommand(
        @NotBlank(message = "Organization ID is required")
        String organizationId
) {
}
