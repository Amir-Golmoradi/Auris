package dev.auris.organization.application.command.update_organization_name;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.domain.value_object.OrganizationName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateOrganizationNameHandler {
    private final LoadOrganizationPort loadOrganizationPort;
    private final SaveOrganizationPort saveOrganizationPort;

    public UpdateOrganizationNameHandler(LoadOrganizationPort loadOrganizationPort, SaveOrganizationPort saveOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
        this.saveOrganizationPort = saveOrganizationPort;
    }

    @Transactional
    public UpdateOrganizationNameResponse handle(UpdateOrganizationNameCommand command) {
        var organization = loadOrganizationPort.loadByOrganizationId(command.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId()));

        organization.updateName(OrganizationName.of(command.newName()));
        var saved = saveOrganizationPort.save(organization);

        return UpdateOrganizationNameResponse.from(saved);
    }
}
