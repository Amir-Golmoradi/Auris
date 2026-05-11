package dev.auris.organization.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrganizationNameRequest(
        @NotBlank(message = "Organization name is required")
        String newName
) {
}
