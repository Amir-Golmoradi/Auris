package dev.auris.organization.application.command.archive_organization;

import jakarta.validation.constraints.NotBlank;

public record ArchiveOrganizationCommand(
        @NotBlank(message = "Organization ID is required")
        String organizationId
) {
}
