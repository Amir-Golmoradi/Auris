package dev.auris.organization.infrastructure.adapter.in.web.dto.response;

public record ReactivateOrganizationResponseDto(
        String organizationId,
        boolean enabled
) {
}
