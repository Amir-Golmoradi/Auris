package dev.auris.organization.application.command.generate_customer_invitation_code;

import dev.auris.organization.domain.enums.InvitationCodeType;
import dev.auris.organization.domain.model.Organization;

public record GenerateCustomerInvitationCodeResponse(
        String organizationId,
        String invitationCode,
        InvitationCodeType invitationCodeType
) {
    public static GenerateCustomerInvitationCodeResponse from(Organization org){
        return new GenerateCustomerInvitationCodeResponse(
                org.getId().getValue().toString(),
                org.getCustomerInvitationCode(),
                InvitationCodeType.CUSTOMER
        );
    }
}
