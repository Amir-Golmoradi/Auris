package dev.auris.organization.infrastructure.adapter.in.web.dto.response;

import dev.auris.organization.domain.enums.InvitationCodeType;

public record GenerateInvitationCodeResponseDto(
        String organizationId,
        String invitationCode,
        InvitationCodeType invitationCodeType
) {
}
