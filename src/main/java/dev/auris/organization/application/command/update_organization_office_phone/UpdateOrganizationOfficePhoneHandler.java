package dev.auris.organization.application.command.update_organization_office_phone;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.domain.value_object.OfficePhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrganizationOfficePhoneHandler {
    private final LoadOrganizationPort loadOrganizationPort;
    private final SaveOrganizationPort saveOrganizationPort;

    public UpdateOrganizationOfficePhoneHandler(LoadOrganizationPort loadOrganizationPort, SaveOrganizationPort saveOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
        this.saveOrganizationPort = saveOrganizationPort;
    }

    @Transactional
    public UpdateOrganizationOfficePhoneResponse handle(UpdateOrganizationOfficePhoneCommand command) {
        var organization = loadOrganizationPort.loadByOrganizationId(command.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId()));

        organization.updateOfficePhoneNumber(OfficePhoneNumber.of(command.newOfficePhoneNumber()));
        var saved = saveOrganizationPort.save(organization);

        return UpdateOrganizationOfficePhoneResponse.from(saved);
    }
}
