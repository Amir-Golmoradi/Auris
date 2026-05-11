package dev.auris.membership.infrastructure.adapter.out.persistence.jpa.model;

import dev.auris.membership.domain.enums.MembershipRole;
import dev.auris.membership.domain.enums.MembershipStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "membership")
public class MembershipJpaEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userAccountId;

    @Column(nullable = false)
    private UUID organizationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime joinedAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    protected MembershipJpaEntity() {
    }

    public MembershipJpaEntity(
            UUID id,
            UUID userAccountId,
            UUID organizationId,
            MembershipRole role,
            MembershipStatus status,
            OffsetDateTime joinedAt,
            OffsetDateTime updatedAt
    ) {
        this.id = id;
        this.userAccountId = userAccountId;
        this.organizationId = organizationId;
        this.role = role;
        this.status = status;
        this.joinedAt = joinedAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserAccountId() {
        return userAccountId;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public MembershipRole getRole() {
        return role;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public OffsetDateTime getJoinedAt() {
        return joinedAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
