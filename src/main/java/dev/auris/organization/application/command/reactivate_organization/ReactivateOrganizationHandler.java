package dev.auris.organization.application.command.reactivate_organization;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReactivateOrganizationHandler {
    private final LoadOrganizationPort loadOrganizationPort;
    private final SaveOrganizationPort saveOrganizationPort;

    public ReactivateOrganizationHandler(LoadOrganizationPort loadOrganizationPort, SaveOrganizationPort saveOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
        this.saveOrganizationPort = saveOrganizationPort;
    }

    @Transactional
    public ReactivateOrganizationResponse handle(ReactivateOrganizationCommand command) {
        var organization = loadOrganizationPort.loadByOrganizationId(command.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId()));

        organization.reactivate();
        var saved = saveOrganizationPort.save(organization);

        return ReactivateOrganizationResponse.from(saved);
    }
}
