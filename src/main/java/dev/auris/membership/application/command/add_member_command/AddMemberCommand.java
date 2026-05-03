package dev.auris.membership.application.command.add_member_command;

import jakarta.validation.constraints.NotBlank;

public record AddMemberCommand(

        @NotBlank(message = "userAccountId must not be blank")
        String userAccountId,

        @NotBlank(message = "organizationId must not be blank")
        String organizationId
) {
}

