package dev.auris.membership.application.command.change_membership_role_account;

import dev.auris.membership.domain.exception.MembershipNotFoundException;
import dev.auris.membership.domain.port.out.LoadMembershipPort;
import dev.auris.membership.domain.port.out.SaveMembershipPort;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeMembershipRoleHandler {
    private final LoadMembershipPort loadMembershipPort;
    private final SaveMembershipPort saveMembershipPort;

    public ChangeMembershipRoleHandler(LoadMembershipPort loadMembershipPort, SaveMembershipPort saveMembershipPort) {
        this.loadMembershipPort = loadMembershipPort;
        this.saveMembershipPort = saveMembershipPort;
    }

    @Transactional
    public void handle(ChangeMembershipRoleCommand command) {
        var organizationId = OrganizationId.fromString(command.organizationId());
        var actorId = UserAccountId.fromString(command.actorUserAccountId());
        var targetId = UserAccountId.fromString(command.targetUserAccountId());

        var actor = loadMembershipPort.loadByUserAndOrganization(actorId, organizationId)
                .orElseThrow(() -> MembershipNotFoundException.forUser(actorId, organizationId));

        var target = loadMembershipPort.loadByUserAndOrganization(targetId, organizationId)
                .orElseThrow(() -> MembershipNotFoundException.forUser(targetId, organizationId));

        target.changeRole(command.parsedRole(), actor);
        saveMembershipPort.save(target);
    }
}
