package dev.auris.organization.infrastructure.adapter.in.web.dto.response;

public record GetOrganizationProfileResponseDto(
        String organizationId,
        String name,
        String officePhoneNumber
) {
}
