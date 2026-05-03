package dev.auris.membership.domain.model;

import dev.auris.membership.domain.enums.MembershipRole;
import dev.auris.membership.domain.enums.MembershipStatus;
import dev.auris.membership.domain.enums.SuspensionReason;
import dev.auris.membership.domain.event.MemberPromotedToOwnerEvent;
import dev.auris.membership.domain.event.MembershipCreatedEvent;
import dev.auris.membership.domain.event.MembershipReactivatedEvent;
import dev.auris.membership.domain.event.MembershipRoleChangedEvent;
import dev.auris.membership.domain.event.MembershipSuspendedEvent;
import dev.auris.membership.domain.exception.MembershipStateException;
import dev.auris.membership.domain.policy.MemberMustHoldRolePolicy;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.shared.building_block.AggregateRoot;
import dev.auris.shared.building_block.DomainEvent;
import dev.auris.user_account.domain.value_object.UserAccountId;

import java.time.OffsetDateTime;

public class Membership extends AggregateRoot<MembershipId, DomainEvent> {
    private final UserAccountId userAccountId;
    private final OrganizationId organizationId;
    private final OffsetDateTime joinedAt;
    private MembershipRole role;
    private MembershipStatus status;
    private OffsetDateTime updatedAt;

    private Membership(
            MembershipId id,
            UserAccountId userAccountId,
            OrganizationId organizationId,
            MembershipRole role,
            MembershipStatus status,
            OffsetDateTime joinedAt,
            OffsetDateTime updatedAt
    ) {
        super(id);
        this.userAccountId = userAccountId;
        this.organizationId = organizationId;
        this.role = role;
        this.status = status;
        this.joinedAt = joinedAt;
        this.updatedAt = updatedAt;
    }

    public static Membership create(
            MembershipId id,
            UserAccountId userAccountId,
            OrganizationId organizationId,
            MembershipRole role
    ) {
        var now = OffsetDateTime.now();
        var membership = new Membership(id, userAccountId, organizationId, role, MembershipStatus.ACTIVE, now, now);
        membership.addDomainEvent(new MembershipCreatedEvent(id, userAccountId, organizationId, role));
        return membership;
    }

    public static Membership reconstruct(
            MembershipId id,
            UserAccountId userAccountId,
            OrganizationId organizationId,
            MembershipRole role,
            MembershipStatus status,
            OffsetDateTime joinedAt,
            OffsetDateTime updatedAt
    ) {
        return new Membership(id, userAccountId, organizationId, role, status, joinedAt, updatedAt);
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

    public UserAccountId getUserAccountId() {
        return userAccountId;
    }

    public OrganizationId getOrganizationId() {
        return organizationId;
    }

    public void promoteToOwner(Membership actor) {
        ensureIsActive();
        new MemberMustHoldRolePolicy(MembershipRole.OWNER).enforce(actor);
        this.role = MembershipRole.OWNER;
        this.updatedAt = OffsetDateTime.now();
        addDomainEvent(new MemberPromotedToOwnerEvent(this.getId(), userAccountId, organizationId));
    }

    public void changeRole(MembershipRole newRole, Membership actor) {
        ensureIsActive();
        new MemberMustHoldRolePolicy(MembershipRole.ADMIN).enforce(actor);
        this.role = newRole;
        this.updatedAt = OffsetDateTime.now();
        addDomainEvent(new MembershipRoleChangedEvent(this.getId(), userAccountId, newRole));
    }

    public void suspend(Membership actor) {
        ensureIsActive();
        new MemberMustHoldRolePolicy(MembershipRole.ADMIN).enforce(actor);
        this.status = MembershipStatus.SUSPENDED;
        this.updatedAt = OffsetDateTime.now();
        addDomainEvent(new MembershipSuspendedEvent(this.getId(), SuspensionReason.MANUAL));
    }

    public void suspendDueToAccountDeactivation() {
        if (this.status == MembershipStatus.SUSPENDED) {
            return;
        }
        this.status = MembershipStatus.SUSPENDED;
        this.updatedAt = OffsetDateTime.now();
        addDomainEvent(new MembershipSuspendedEvent(this.getId(), SuspensionReason.ACCOUNT_DEACTIVATED));
    }

    public void reactivate(Membership actor) {
        ensureIsSuspended();
        new MemberMustHoldRolePolicy(MembershipRole.ADMIN).enforce(actor);
        this.status = MembershipStatus.ACTIVE;
        this.updatedAt = OffsetDateTime.now();
        addDomainEvent(new MembershipReactivatedEvent(this.getId(), userAccountId));
    }

    private void ensureIsActive() {
        if (this.status != MembershipStatus.ACTIVE) {
            throw MembershipStateException.notActive(this.getId());
        }
    }

    private void ensureIsSuspended() {
        if (this.status != MembershipStatus.SUSPENDED) {
            throw MembershipStateException.notSuspended(this.getId());
        }
    }
}
