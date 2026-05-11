package dev.auris.organization.application.query.get_organization_by_id;

import dev.auris.organization.domain.model.Organization;

import java.time.OffsetDateTime;

public record GetOrganizationByIdResponse(
        String organizationId,
        String name,
        String officePhoneNumber,
        boolean enabled,
        OffsetDateTime createdAt
) {
    public static GetOrganizationByIdResponse from(Organization organization) {
        return new GetOrganizationByIdResponse(
                organization.getId().getValue().toString(),
                organization.getName().getValue(),
                organization.getOfficePhoneNumber().getNumber(),
                organization.getIsOrganizationEnabled(),
                organization.getCreatedAt()
        );
    }
}
