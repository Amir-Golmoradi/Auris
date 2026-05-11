package dev.auris.organization.application.command.deactivate_organization;

import dev.auris.organization.domain.model.Organization;

public record DeActivateOrganizationResponse(
        String organizationId,
        boolean enabled
) {
    public static DeActivateOrganizationResponse from(Organization organization) {
        return new DeActivateOrganizationResponse(
                organization.getId().getValue().toString(),
                organization.getIsOrganizationEnabled()
        );
    }
}
