package dev.auris.organization.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrganizationOfficePhoneRequest(
        @NotBlank(message = "Office phone number is required")
        String newOfficePhoneNumber
) {
}
