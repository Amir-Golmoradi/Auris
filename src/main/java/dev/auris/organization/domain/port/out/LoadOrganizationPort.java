package dev.auris.organization.domain.port.out;

import dev.auris.organization.domain.model.Organization;

import java.util.Optional;

public interface LoadOrganizationPort {
    Optional<Organization> loadByOrganizationId(String OrganizationId);
}
