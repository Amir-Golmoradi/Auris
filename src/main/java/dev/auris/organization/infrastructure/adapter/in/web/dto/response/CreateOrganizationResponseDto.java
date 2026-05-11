package dev.auris.organization.infrastructure.adapter.in.web.dto.response;

import java.time.OffsetDateTime;

public record CreateOrganizationResponseDto(
        String organizationId,
        String name,
        String officePhoneNumber,
        boolean enabled,
        OffsetDateTime createdAt
) {
}
