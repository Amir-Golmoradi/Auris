package dev.auris.organization.infrastructure.adapter.in.web.dto.response;

public record UpdateOrganizationNameResponseDto(
        String organizationId,
        String name
) {
}
