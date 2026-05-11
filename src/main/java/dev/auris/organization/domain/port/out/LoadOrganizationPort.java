package dev.auris.organization.domain.port.out;

import dev.auris.organization.domain.model.Organization;

import java.util.List;
import java.util.Optional;

public interface LoadOrganizationPort {
    Optional<Organization> loadByOrganizationId(String organizationId);
    Optional<Organization> loadByName(String name);
    List<Organization> loadAll();
}
