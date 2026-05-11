package dev.auris.organization.infrastructure.adapter.out.persistence.jpa.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
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
    private OffsetDateTime createdAt;

    private String officePhoneNumber;

    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(name = "member_invitation_code", unique = true)
    @Access(AccessType.FIELD)
    private String memberInvitationCode;

    @Column(name = "customer_invitation_code", unique = true)
    @Access(AccessType.FIELD)
    private String customerInvitationCode;

    private OrganizationJpaEntity(UUID id) {
        this.id = id;
    }

    protected OrganizationJpaEntity() {
    }

    private OrganizationJpaEntity(
            UUID id,
            String name,
            OffsetDateTime createdAt,
            String officePhoneNumber,
            Boolean isEnabled,
            String memberInvitationCode,
            String customerInvitationCode
    ) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.officePhoneNumber = officePhoneNumber;
        this.isEnabled = isEnabled;
        this.memberInvitationCode = memberInvitationCode;
        this.customerInvitationCode = customerInvitationCode;
    }

    public static OrganizationJpaEntity create(String name, String officePhoneNumber, String memberInvitationCode) {
        return new OrganizationJpaEntity(
                null,
                name,
                OffsetDateTime.now(),
                officePhoneNumber,
                true,
                memberInvitationCode,
                null
        );
    }

    public static OrganizationJpaEntity create(
            UUID id,
            String name,
            OffsetDateTime createdAt,
            String officePhoneNumber,
            Boolean isEnabled,
            String memberInvitationCode,
            String customerInvitationCode
    ) {
        return new OrganizationJpaEntity(
                id,
                name,
                createdAt,
                officePhoneNumber,
                isEnabled,
                memberInvitationCode,
                customerInvitationCode
        );
    }

    public UUID getId() {
        return id;
    }

    public String getInvitationCode() {
        return memberInvitationCode;
    }

    public String getMemberInvitationCode() {
        return memberInvitationCode;
    }

    public String getCustomerInvitationCode() {
        return customerInvitationCode;
    }

    public String getName() {
        return name;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setInvitationCode(String invitationCode) {
        this.memberInvitationCode = invitationCode;
    }
}
