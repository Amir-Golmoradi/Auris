package dev.auris.organization.application.command.reactivate_organization;

import dev.auris.organization.domain.model.Organization;

public record ReactivateOrganizationResponse(
        String organizationId,
        boolean enabled
) {
    public static ReactivateOrganizationResponse from(Organization organization) {
        return new ReactivateOrganizationResponse(
                organization.getId().getValue().toString(),
                organization.getIsOrganizationEnabled()
        );
    }
}
