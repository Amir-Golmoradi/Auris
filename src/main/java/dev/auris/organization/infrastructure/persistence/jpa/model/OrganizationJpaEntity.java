package dev.auris.organization.infrastructure.persistence.jpa.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "organization")
public class OrganizationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private String officePhoneNumber;

    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(name = "invitation_code", unique = true)
    @Access(AccessType.FIELD)
    private String invitationCode;

    private OrganizationJpaEntity(UUID id) {
        this.id = id;
    }

    protected OrganizationJpaEntity() {
    }

    private OrganizationJpaEntity(UUID id, String name, LocalDateTime createdAt, String officePhoneNumber, Boolean isEnabled, String invitationCode) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.officePhoneNumber = officePhoneNumber;
        this.isEnabled = isEnabled;
        this.invitationCode = invitationCode;
    }

    public static OrganizationJpaEntity create(String name, String officePhoneNumber, String invitationCode) {
        return new OrganizationJpaEntity(
                null,
                name,
                LocalDateTime.now(),
                officePhoneNumber,
                true,
                invitationCode
        );
    }

    public UUID getId() {
        return id;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public String getName() {
        return name;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
