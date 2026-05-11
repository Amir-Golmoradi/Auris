package dev.auris.organization.domain.model;

import dev.auris.organization.domain.event.CustomerInvitationCodeAssignedEvent;
import dev.auris.organization.domain.event.MemberInvitationCodeAssignedEvent;
import dev.auris.organization.domain.exception.InvalidInvitationCodeException;
import dev.auris.organization.domain.exception.InvitationCodeNotFoundException;
import dev.auris.organization.domain.exception.OrganizationStateException;
import dev.auris.organization.domain.service.InvitationCodeGenerator;
import dev.auris.organization.domain.value_object.InvitationCode;
import dev.auris.organization.domain.value_object.OfficePhoneNumber;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.organization.domain.value_object.OrganizationName;
import dev.auris.shared.building_block.AggregateRoot;
import dev.auris.shared.building_block.DomainEvent;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Organization extends AggregateRoot<OrganizationId, DomainEvent> {

    private final OrganizationId id;
    private final OffsetDateTime createdAt;
    private OrganizationName name;
    private OfficePhoneNumber officePhoneNumber;
    private InvitationCode memberInvitationCode;
    private InvitationCode customerInvitationCode;
    private boolean isOrganizationEnabled;
    private Long version;

    // ── Constructors ────────────────────────────────────────────────────────────

    public Organization() {
        super(OrganizationId.generate());
        this.id = getId();
        this.createdAt = OffsetDateTime.now();
        this.isOrganizationEnabled = true;
    }

    private Organization(OrganizationName name, OfficePhoneNumber officePhoneNumber) {
        super(OrganizationId.generate());
        this.id = getId();
        this.name = name;
        this.officePhoneNumber = officePhoneNumber;
        this.memberInvitationCode = null;
        this.customerInvitationCode = null;
        this.createdAt = OffsetDateTime.now();
        this.isOrganizationEnabled = true;
        this.version = null;
    }

    private Organization(
            OrganizationId id,
            OrganizationName name,
            OfficePhoneNumber officePhoneNumber,
            InvitationCode memberInvitationCode,
            InvitationCode customerInvitationCode,
            OffsetDateTime createdAt,
            boolean isOrganizationEnabled,
            Long version
    ) {
        super(id);
        this.id = Objects.requireNonNull(id, "OrganizationId must not be null");
        this.name = Objects.requireNonNull(name, "OrganizationName must not be null");
        this.officePhoneNumber = Objects.requireNonNull(officePhoneNumber, "OfficePhoneNumber must not be null");
        this.memberInvitationCode = memberInvitationCode;
        this.customerInvitationCode = customerInvitationCode;
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt must not be null");
        this.isOrganizationEnabled = isOrganizationEnabled;
        this.version = version;
    }

    // ── Factories ───────────────────────────────────────────────────────────────

    public static Organization create(OrganizationName name, OfficePhoneNumber officePhoneNumber) {
        return new Organization(name, officePhoneNumber);
    }

    public static Organization reconstruct(
            OrganizationId id,
            OrganizationName name,
            OfficePhoneNumber officePhoneNumber,
            InvitationCode memberInvitationCode,
            InvitationCode customerInvitationCode,
            OffsetDateTime createdAt,
            boolean isOrganizationEnabled,
            Long version
    ) {
        return new Organization(
                id,
                name,
                officePhoneNumber,
                memberInvitationCode,
                customerInvitationCode,
                createdAt,
                isOrganizationEnabled,
                version
        );
    }

    public static Organization reconstruct(
            OrganizationId id,
            OrganizationName name,
            OfficePhoneNumber officePhoneNumber,
            String invitationCode,
            OffsetDateTime createdAt,
            boolean isOrganizationEnabled
    ) {
        InvitationCode memberInvitationCode = invitationCode == null || invitationCode.isBlank()
                ? null
                : InvitationCode.of(invitationCode);
        return reconstruct(
                id,
                name,
                officePhoneNumber,
                memberInvitationCode,
                null,
                createdAt,
                isOrganizationEnabled,
                null
        );
    }

    // ── Invitation Code Behavior ────────────────────────────────────────────────

    public void assignMemberInvitationCode(InvitationCodeGenerator codeGenerator) {
        ensureIsEnabled();
        this.memberInvitationCode = requireValidGeneratedCode(codeGenerator);
        var event = new MemberInvitationCodeAssignedEvent(id, memberInvitationCode);
        addDomainEvent(event);
    }

    public void assignCustomerInvitationCode(InvitationCodeGenerator codeGenerator) {
        ensureIsEnabled();
        this.customerInvitationCode = requireValidGeneratedCode(codeGenerator);
        var event = new CustomerInvitationCodeAssignedEvent(id, customerInvitationCode);
        addDomainEvent(event);
    }

    public void consumeCustomerInvitationCode(InvitationCode provided) {
        ensureIsEnabled();
        if (this.customerInvitationCode == null) {
            throw new InvitationCodeNotFoundException();
        }
        if (!this.customerInvitationCode.equals(provided)) {
            throw new InvalidInvitationCodeException();
        }
        this.customerInvitationCode = null;
    }

    // ── State Transitions ───────────────────────────────────────────────────────

    public void updateName(OrganizationName updatedName) {
        ensureIsEnabled();
        this.name = Objects.requireNonNull(updatedName, "OrganizationName must not be null");
    }

    public void updateOfficePhoneNumber(OfficePhoneNumber updatedOfficePhoneNumber) {
        ensureIsEnabled();
        this.officePhoneNumber = Objects.requireNonNull(updatedOfficePhoneNumber, "OfficePhoneNumber must not be null");
    }

    public void archive() {
        ensureIsEnabled();
        this.isOrganizationEnabled = false;
    }

    public void reactivate() {
        if (isOrganizationEnabled) {
            throw OrganizationStateException.alreadyActive(id);
        }
        this.isOrganizationEnabled = true;
    }

    public void deactivate() {
        archive();
    }

    // ── Guard ───────────────────────────────────────────────────────────────────

    private void ensureIsEnabled() {
        if (!isOrganizationEnabled) {
            throw OrganizationStateException.notActive(id);
        }
    }

    private InvitationCode requireValidGeneratedCode(InvitationCodeGenerator codeGenerator) {
        Objects.requireNonNull(codeGenerator, "InvitationCodeGenerator must not be null");
        InvitationCode generatedCode = codeGenerator.generate(8);
        if (generatedCode == null) {
            throw new InvalidInvitationCodeException();
        }
        return generatedCode;
    }

    private String invitationCodeValue(InvitationCode invitationCode) {
        if (invitationCode == null) {
            return null;
        }
        return String.valueOf(invitationCode.getAtomicValues().getFirst());
    }

    // ── Getters ─────────────────────────────────────────────────────────────────

    public OrganizationId getId() {
        return id;
    }

    public OrganizationName getName() {
        return name;
    }

    public OfficePhoneNumber getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public String getInvitationCode() {
        return getMemberInvitationCode();
    }

    public String getMemberInvitationCode() {
        return invitationCodeValue(memberInvitationCode);
    }

    public String getCustomerInvitationCode() {
        return invitationCodeValue(customerInvitationCode);
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean getIsOrganizationEnabled() {
        return isOrganizationEnabled;
    }

    public Long getVersion() {
        return version;
    }
}
