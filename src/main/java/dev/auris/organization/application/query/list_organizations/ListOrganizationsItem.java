package dev.auris.organization.application.query.list_organizations;

import dev.auris.organization.domain.model.Organization;

import java.time.OffsetDateTime;

public record ListOrganizationsItem(
        String organizationId,
        String name,
        String officePhoneNumber,
        boolean enabled,
        OffsetDateTime createdAt
) {
    public static ListOrganizationsItem from(Organization organization) {
        return new ListOrganizationsItem(
                organization.getId().getValue().toString(),
                organization.getName().getValue(),
                organization.getOfficePhoneNumber().getNumber(),
                organization.getIsOrganizationEnabled(),
                organization.getCreatedAt()
        );
    }
}
