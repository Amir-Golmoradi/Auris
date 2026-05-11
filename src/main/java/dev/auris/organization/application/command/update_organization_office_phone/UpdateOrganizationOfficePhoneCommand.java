package dev.auris.organization.application.command.update_organization_office_phone;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrganizationOfficePhoneCommand(
        @NotBlank(message = "Organization ID is required")
        String organizationId,

        @NotBlank(message = "Office phone number is required")
        String newOfficePhoneNumber
) {
}
