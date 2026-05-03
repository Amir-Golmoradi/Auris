package dev.auris.membership.domain.exception;

import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;

public final class MembershipStateException extends RuntimeException {

    private MembershipStateException(String message) {
        super(message);
    }

    public static MembershipStateException notActive(MembershipId id) {
        return new MembershipStateException(
                "Membership [%s] is not active".formatted(id)
        );
    }

    public static MembershipStateException notSuspended(MembershipId id) {
        return new MembershipStateException(
                "Membership [%s] is not suspended".formatted(id)
        );
    }

    public static MembershipStateException alreadyMember(
            UserAccountId userId, OrganizationId orgId) {
        return new MembershipStateException(
                "User [%s] is already a member of organization [%s]"
                        .formatted(userId, orgId)
        );
    }
}