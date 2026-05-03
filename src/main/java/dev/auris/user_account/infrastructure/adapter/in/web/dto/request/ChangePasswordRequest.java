package dev.auris.user_account.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "لطفا رمز عبور جدید خود را وارد کنید")
        String newPassword
) {
}
