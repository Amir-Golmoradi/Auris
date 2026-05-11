package dev.auris.membership.domain.exception;

import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;

public final class MembershipNotFoundException extends RuntimeException {

    private MembershipNotFoundException(String message) { super(message); }

    public static MembershipNotFoundException forUser(
            UserAccountId userId, OrganizationId organizationId) {
        return new MembershipNotFoundException(
                "No membership found for user [%s] in organization [%s]"
                        .formatted(userId, organizationId)
        );
    }
}