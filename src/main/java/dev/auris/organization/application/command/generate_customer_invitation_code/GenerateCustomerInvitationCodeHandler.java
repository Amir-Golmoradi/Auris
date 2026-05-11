package dev.auris.organization.application.command.generate_customer_invitation_code;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.domain.service.InvitationCodeGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenerateCustomerInvitationCodeHandler {
    private final InvitationCodeGenerator codeGenerator;
    private final LoadOrganizationPort loadOrganizationPort;
    private final SaveOrganizationPort saveOrganizationPort;

    public GenerateCustomerInvitationCodeHandler(InvitationCodeGenerator codeGenerator, LoadOrganizationPort loadOrganizationPort, SaveOrganizationPort saveOrganizationPort) {
        this.codeGenerator = codeGenerator;
        this.loadOrganizationPort = loadOrganizationPort;
        this.saveOrganizationPort = saveOrganizationPort;
    }


    @Transactional
    public GenerateCustomerInvitationCodeResponse handle(GenerateCustomerInvitationCodeCommand command) {
        var organization = loadOrganizationPort.loadByOrganizationId(command.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId()));
        organization.assignCustomerInvitationCode(codeGenerator);
        var savedCustomer = saveOrganizationPort.save(organization);

        return GenerateCustomerInvitationCodeResponse.from(savedCustomer);
    }

}
