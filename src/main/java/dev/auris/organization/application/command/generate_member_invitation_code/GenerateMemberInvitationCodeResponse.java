package dev.auris.organization.application.command.generate_member_invitation_code;

import dev.auris.organization.domain.enums.InvitationCodeType;
import dev.auris.organization.domain.model.Organization;

public record GenerateMemberInvitationCodeResponse(
        String organizationId,
        String invitationCode,
        InvitationCodeType invitationCodeType
) {
    public static GenerateMemberInvitationCodeResponse from(Organization organization) {
        return new GenerateMemberInvitationCodeResponse(
                organization.getId().getValue().toString(),
                organization.getMemberInvitationCode(),
                InvitationCodeType.MEMBER
        );
    }
}
