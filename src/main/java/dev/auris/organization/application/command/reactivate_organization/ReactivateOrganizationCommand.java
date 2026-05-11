package dev.auris.organization.application.command.reactivate_organization;

import jakarta.validation.constraints.NotBlank;

public record ReactivateOrganizationCommand(
        @NotBlank(message = "Organization ID is required")
        String organizationId
) {
}
