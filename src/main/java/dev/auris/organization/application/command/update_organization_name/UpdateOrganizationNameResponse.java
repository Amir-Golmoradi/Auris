package dev.auris.organization.application.command.update_organization_name;

import dev.auris.organization.domain.model.Organization;

public record UpdateOrganizationNameResponse(
        String organizationId,
        String name
) {
    public static UpdateOrganizationNameResponse from(Organization organization) {
        return new UpdateOrganizationNameResponse(
                organization.getId().getValue().toString(),
                organization.getName().getValue()
        );
    }
}
