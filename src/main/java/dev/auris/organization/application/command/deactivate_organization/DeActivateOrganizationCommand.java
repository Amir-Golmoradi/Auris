package dev.auris.organization.application.command.deactivate_organization;

import jakarta.validation.constraints.NotBlank;

public record DeActivateOrganizationCommand(
        @NotBlank(message = "Organization ID is required")
        String organizationId
) {
}
