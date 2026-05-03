package dev.auris.organization.application.command.create_organization;

import dev.auris.organization.domain.model.Organization;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.domain.service.InvitationCodeGenerator;
import dev.auris.organization.domain.value_object.OfficePhoneNumber;
import dev.auris.organization.domain.value_object.OrganizationName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOrganizationHandler {
    private final InvitationCodeGenerator codeGenerator;
    private final SaveOrganizationPort saveOrganizationPort;

    public CreateOrganizationHandler(InvitationCodeGenerator codeGenerator, SaveOrganizationPort saveOrganizationPort) {
        this.codeGenerator = codeGenerator;
        this.saveOrganizationPort = saveOrganizationPort;
    }

    @Transactional
    public Organization handle(CreateOrganizationCommand command) {
        var organization = Organization.create(
                OrganizationName.of(command.name()),
                OfficePhoneNumber.of(command.officePhoneNumber())
        );
        organization.assignInvitationCode(codeGenerator);
        return saveOrganizationPort.save(organization);
    }
}
