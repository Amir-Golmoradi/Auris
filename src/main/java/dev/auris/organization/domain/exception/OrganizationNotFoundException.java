package dev.auris.organization.domain.exception;

public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(String organizationId) {
        super("Organization not found: %s".formatted(organizationId));
    }
}
