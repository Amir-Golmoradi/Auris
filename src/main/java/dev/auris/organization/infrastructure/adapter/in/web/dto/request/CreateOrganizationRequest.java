package dev.auris.organization.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateOrganizationRequest(
        @NotBlank(message = "لطفا نام سازمان خود را وارد کنید")
        String name,

        @NotBlank(message = "لطفا شماره تلفن دفتر را وارد کنید")
        String officePhoneNumber
) {
}
