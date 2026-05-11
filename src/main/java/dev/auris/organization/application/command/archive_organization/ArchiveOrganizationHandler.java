package dev.auris.organization.application.command.archive_organization;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArchiveOrganizationHandler {
    private final LoadOrganizationPort loadOrganizationPort;
    private final SaveOrganizationPort saveOrganizationPort;

    public ArchiveOrganizationHandler(LoadOrganizationPort loadOrganizationPort, SaveOrganizationPort saveOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
        this.saveOrganizationPort = saveOrganizationPort;
    }

    @Transactional
    public ArchiveOrganizationResponse handle(ArchiveOrganizationCommand command) {
        var organization = loadOrganizationPort.loadByOrganizationId(command.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.organizationId()));

        organization.archive();
        var saved = saveOrganizationPort.save(organization);

        return ArchiveOrganizationResponse.from(saved);
    }
}
