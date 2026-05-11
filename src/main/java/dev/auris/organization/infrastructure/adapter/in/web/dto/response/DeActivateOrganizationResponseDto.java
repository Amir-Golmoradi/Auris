package dev.auris.organization.infrastructure.adapter.in.web.dto.response;

public record DeActivateOrganizationResponseDto(
        String organizationId,
        boolean enabled
) {
}
