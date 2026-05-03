package dev.auris.organization.domain.port.out;

import dev.auris.organization.domain.model.Organization;

public interface SaveOrganizationPort {
    Organization save(Organization organization);
}