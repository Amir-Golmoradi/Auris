package dev.auris.organization.application.query.get_organization_invitation_code;

import dev.auris.organization.domain.exception.InvitationCodeNotFoundException;
import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import org.springframework.stereotype.Service;

@Service
public class GetOrganizationInvitationCodeHandler {
    private final LoadOrganizationPort loadOrganizationPort;

    public GetOrganizationInvitationCodeHandler(LoadOrganizationPort loadOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
    }

    public GetOrganizationInvitationCodeResponse handleMemberInvitationCode(GetOrganizationInvitationCodeQuery query) {
        var organization = loadOrganizationPort.loadByOrganizationId(query.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(query.organizationId()));

        if (organization.getMemberInvitationCode() == null || organization.getMemberInvitationCode().isBlank()) {
            throw new InvitationCodeNotFoundException();
        }

        return GetOrganizationInvitationCodeResponse.memberFrom(organization);
    }

    public GetOrganizationInvitationCodeResponse handleCustomerInvitationCode(GetOrganizationInvitationCodeQuery query) {
        var organization = loadOrganizationPort.loadByOrganizationId(query.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(query.organizationId()));

        if (organization.getCustomerInvitationCode() == null || organization.getCustomerInvitationCode().isBlank()) {
            throw new InvitationCodeNotFoundException();
        }

        return GetOrganizationInvitationCodeResponse.customerFrom(organization);
    }
}
