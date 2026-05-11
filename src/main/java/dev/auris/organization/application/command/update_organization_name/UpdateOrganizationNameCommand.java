package dev.auris.organization.application.command.update_organization_name;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrganizationNameCommand(
        @NotBlank(message = "Organization ID is required")
        String organizationId,

        @NotBlank(message = "Organization name is required")
        String newName
) {
}
