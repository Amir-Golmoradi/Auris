package dev.auris.user_account.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeFullNameRequest(
        @NotBlank(message = "لطفا نام خود را وارد کنید")
        String firstName,
        @NotBlank(message = "لطفا نام خانوادگی خود را وارد کنید")
        String lastName
) {
}
