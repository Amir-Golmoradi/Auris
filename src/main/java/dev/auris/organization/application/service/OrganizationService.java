package dev.auris.organization.application.service;

import dev.auris.organization.domain.value_object.OrganizationId;

public interface OrganizationService {
    String readInvitationCode(OrganizationId organizationId);
    String generateInvitationCode(OrganizationId organizationId);
}
