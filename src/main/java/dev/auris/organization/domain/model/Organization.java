package dev.auris.organization.domain.model;

import dev.auris.organization.domain.exception.InvalidInvitationCodeException;
import dev.auris.organization.domain.service.InvitationCodeGenerator;
import dev.auris.organization.domain.value_object.OfficePhoneNumber;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.organization.domain.value_object.OrganizationName;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Organization {
    private OrganizationId id;
    private OrganizationName name;
    private OffsetDateTime createdAt;
    private boolean isOrganizationEnabled;
    private OfficePhoneNumber officePhoneNumber;
    private String invitationCode;

    public Organization() {
    }

    private Organization(OrganizationName name, OfficePhoneNumber officePhoneNumber) {
        this.id = OrganizationId.generate();
        this.name = name;
        this.officePhoneNumber = officePhoneNumber;
        this.createdAt = OffsetDateTime.now();
        this.isOrganizationEnabled = true;
    }

    private Organization(OrganizationId id, OrganizationName name, OfficePhoneNumber officePhoneNumber, String invitationCode) {
        this.id = Objects.requireNonNull(id, "OrganizationId cannot be null");
        this.name = name;
        this.officePhoneNumber = officePhoneNumber;
        this.invitationCode = invitationCode;
    }

    public static Organization create(OrganizationName name, OfficePhoneNumber phoneNumber) {
        return new Organization(name, phoneNumber);
    }

    public static Organization reconstruct(OrganizationId id, OrganizationName name, OfficePhoneNumber phoneNumber, String invitationCode) {
        return new Organization(id, name, phoneNumber, invitationCode);
    }

    public OrganizationId getId() {
        return id;
    }

    public OrganizationName getName() {
        return name;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean getIsOrganizationEnabled() {
        return isOrganizationEnabled;
    }

    public OfficePhoneNumber getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void assignInvitationCode(InvitationCodeGenerator codeGenerator) {
        Objects.requireNonNull(codeGenerator, "InvitationCodeGenerator cannot be null");
        String newCode = codeGenerator.generate(8);
        if (newCode == null || newCode.isBlank()) {
            throw new InvalidInvitationCodeException();
        }
        this.invitationCode = newCode;
    }
}
