package dev.auris.user_account.application.command.register_new_owner_account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterNewOwnerCommand(
        @NotBlank
        @Email(message = "لطفا ایمیل خود را وارد کنید")
        String email,
        @NotBlank(message = "لطفا نام کاربری خود را وارد کنید")
        String username,
        @NotBlank(message = "لطفا رمز عبور خود را وارد کنید")
        String password,
        @NotBlank(message = "لطفا نام خود را وارد کنید")
        String firstName,
        @NotBlank(message = "لطفا نام خانوادگی خود را وارد کنید")
        String lastName,
        @NotBlank(message = "لطفا تلفن همراه خود را وارد کنید")
        String phoneNumber,
        String description,
        @NotBlank(message = "لطفا نام سازمان خود را وارد کنید")
        String organizationName,
        @NotBlank(message = "لطفا شماره تلفن دفتر را وارد کنید")
        String officePhoneNumber
) {
}
