package dev.auris.organization.application.query.get_organization_by_id;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import org.springframework.stereotype.Service;

@Service
public class GetOrganizationByIdHandler {
    private final LoadOrganizationPort loadOrganizationPort;

    public GetOrganizationByIdHandler(LoadOrganizationPort loadOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
    }

    public GetOrganizationByIdResponse handle(GetOrganizationByIdQuery query) {
        var organization = loadOrganizationPort.loadByOrganizationId(query.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(query.organizationId()));

        return GetOrganizationByIdResponse.from(organization);
    }
}
