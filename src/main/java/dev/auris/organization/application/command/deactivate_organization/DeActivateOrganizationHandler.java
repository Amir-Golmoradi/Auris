package dev.auris.organization.application.command.deactivate_organization;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeActivateOrganizationHandler {
    private final LoadOrganizationPort loadOrganizationPort;
    private final SaveOrganizationPort saveOrganizationPort;

    public DeActivateOrganizationHandler(LoadOrganizationPort loadOrganizationPort, SaveOrganizationPort saveOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
        this.saveOrganizationPort = saveOrganizationPort;
    }

    @Transactional
    public DeActivateOrganizationResponse handle(DeActivateOrganizationCommand command) {
        var organization = loadOrganizationPort.loadByOrganizationId(command.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId()));

        organization.deactivate();
        var saved = saveOrganizationPort.save(organization);

        return DeActivateOrganizationResponse.from(saved);
    }
}
