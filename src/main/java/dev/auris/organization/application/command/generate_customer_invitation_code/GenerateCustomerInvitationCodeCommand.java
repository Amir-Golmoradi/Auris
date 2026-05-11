package dev.auris.organization.application.command.generate_customer_invitation_code;

import jakarta.validation.constraints.NotBlank;

public record GenerateCustomerInvitationCodeCommand(
        @NotBlank(message = "Cannot be null")
        String organizationId
) {
}

