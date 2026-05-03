package dev.auris.organization.application.command.create_organization;

import jakarta.validation.constraints.NotBlank;

public record CreateOrganizationCommand(
        @NotBlank(message = "لطفا نام سازمان خود را وارد کنید")
        String name,
        @NotBlank(message = "لطفا شماره تلفن دفتر را وارد کنید")
        String officePhoneNumber
) {
}
