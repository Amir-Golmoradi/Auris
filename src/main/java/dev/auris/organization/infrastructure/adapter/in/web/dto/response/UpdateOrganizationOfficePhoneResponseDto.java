package dev.auris.organization.infrastructure.adapter.in.web.dto.response;

public record UpdateOrganizationOfficePhoneResponseDto(
        String organizationId,
        String officePhoneNumber
) {
}
