package dev.auris.organization.application.command.archive_organization;

import dev.auris.organization.domain.model.Organization;

public record ArchiveOrganizationResponse(
        String organizationId,
        boolean enabled
) {
    public static ArchiveOrganizationResponse from(Organization organization) {
        return new ArchiveOrganizationResponse(
                organization.getId().getValue().toString(),
                organization.getIsOrganizationEnabled()
        );
    }
}
