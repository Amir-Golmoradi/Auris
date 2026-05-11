package dev.auris.organization.application.command.create_organization;

import dev.auris.organization.domain.model.Organization;

import java.time.OffsetDateTime;

public record CreateOrganizationResponse(
        String organizationId,
        String name,
        String officePhoneNumber,
        boolean enabled,
        OffsetDateTime createdAt
) {
    public static CreateOrganizationResponse from(Organization organization) {
        return new CreateOrganizationResponse(
                organization.getId().getValue().toString(),
                organization.getName().getValue(),
                organization.getOfficePhoneNumber().getNumber(),
                organization.getIsOrganizationEnabled(),
                organization.getCreatedAt()
        );
    }
}
