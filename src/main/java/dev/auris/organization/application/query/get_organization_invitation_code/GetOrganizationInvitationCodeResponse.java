package dev.auris.organization.application.query.get_organization_invitation_code;

import dev.auris.organization.domain.enums.InvitationCodeType;
import dev.auris.organization.domain.model.Organization;

public record GetOrganizationInvitationCodeResponse(
        String organizationId,
        String invitationCode,
        InvitationCodeType invitationCodeType
) {
    public static GetOrganizationInvitationCodeResponse memberFrom(Organization organization) {
        return new GetOrganizationInvitationCodeResponse(
                organization.getId().getValue().toString(),
                organization.getMemberInvitationCode(),
                InvitationCodeType.MEMBER
        );
    }

    public static GetOrganizationInvitationCodeResponse customerFrom(Organization organization) {
        return new GetOrganizationInvitationCodeResponse(
                organization.getId().getValue().toString(),
                organization.getCustomerInvitationCode(),
                InvitationCodeType.CUSTOMER
        );
    }
}
