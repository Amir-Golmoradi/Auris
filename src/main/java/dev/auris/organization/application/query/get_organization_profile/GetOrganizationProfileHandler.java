package dev.auris.organization.application.query.get_organization_profile;

import dev.auris.organization.domain.exception.OrganizationNotFoundException;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import org.springframework.stereotype.Service;

@Service
public class GetOrganizationProfileHandler {
    private final LoadOrganizationPort loadOrganizationPort;

    public GetOrganizationProfileHandler(LoadOrganizationPort loadOrganizationPort) {
        this.loadOrganizationPort = loadOrganizationPort;
    }

    public GetOrganizationProfileResponse handle(GetOrganizationProfileQuery query) {
        var organization = loadOrganizationPort.loadByOrganizationId(query.organizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(query.organizationId()));

        return GetOrganizationProfileResponse.from(organization);
    }
}
