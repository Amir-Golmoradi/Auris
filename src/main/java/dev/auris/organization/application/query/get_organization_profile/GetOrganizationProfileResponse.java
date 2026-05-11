package dev.auris.organization.application.query.get_organization_profile;

import dev.auris.organization.domain.model.Organization;

public record GetOrganizationProfileResponse(
        String organizationId,
        String name,
        String officePhoneNumber
) {
    public static GetOrganizationProfileResponse from(Organization organization) {
        return new GetOrganizationProfileResponse(
                organization.getId().getValue().toString(),
                organization.getName().getValue(),
                organization.getOfficePhoneNumber().getNumber()
        );
    }
}
