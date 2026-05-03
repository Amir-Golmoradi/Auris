package dev.auris.organization.domain.port.out;

import dev.auris.organization.domain.value_object.OrganizationId;

import java.util.Optional;

public interface LoadInvitationCodePort {
    Optional<String> loadInvitationCode(OrganizationId organizationId);
}