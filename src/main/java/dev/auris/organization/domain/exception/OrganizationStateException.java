package dev.auris.organization.domain.exception;

import dev.auris.organization.domain.value_object.OrganizationId;

public class OrganizationStateException extends RuntimeException {
    private OrganizationStateException(String message) {
        super(message);
    }

    public static OrganizationStateException notActive(OrganizationId organizationId) {
        return new OrganizationStateException("Organization is not active: %s".formatted(organizationId.getValue()));
    }

    public static OrganizationStateException alreadyActive(OrganizationId organizationId) {
        return new OrganizationStateException("Organization is already active: %s".formatted(organizationId.getValue()));
    }
}
