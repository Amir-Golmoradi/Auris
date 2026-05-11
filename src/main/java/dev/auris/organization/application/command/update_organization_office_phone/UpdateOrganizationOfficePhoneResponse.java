package dev.auris.organization.application.command.update_organization_office_phone;

import dev.auris.organization.domain.model.Organization;

public record UpdateOrganizationOfficePhoneResponse(
        String organizationId,
        String officePhoneNumber
) {
    public static UpdateOrganizationOfficePhoneResponse from(Organization organization) {
        return new UpdateOrganizationOfficePhoneResponse(
                organization.getId().getValue().toString(),
                organization.getOfficePhoneNumber().getNumber()
        );
    }
}
