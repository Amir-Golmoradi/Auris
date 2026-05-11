package dev.auris.organization.application.command.generate_member_invitation_code;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.domain.service.InvitationCodeGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenerateMemberInvitationCodeHandler {
    private final LoadOrganizationPort loadOrganizationPort;
    private final SaveOrganizationPort saveOrganizationPort;
    private final InvitationCodeGenerator codeGenerator;

    public GenerateMemberInvitationCodeHandler(
            LoadOrganizationPort loadOrganizationPort,
            SaveOrganizationPort saveOrganizationPort,
            InvitationCodeGenerator codeGenerator
    ) {
        this.loadOrganizationPort = loadOrganizationPort;
        this.saveOrganizationPort = saveOrganizationPort;
        this.codeGenerator = codeGenerator;
    }

    @Transactional
    public GenerateMemberInvitationCodeResponse handle(GenerateMemberInvitationCodeCommand command) {
        var organization = loadOrganizationPort.loadByOrganizationId(command.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId()));

        organization.assignMemberInvitationCode(codeGenerator);
        var saved = saveOrganizationPort.save(organization);

        return GenerateMemberInvitationCodeResponse.from(saved);
    }
}
