package dev.auris.user_account.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeEmailRequest(
        @NotBlank
        @Email(message = "لطفا ایمیل خود را وارد کنید")
        String updatedEmail
) {
}
