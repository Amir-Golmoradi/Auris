package dev.auris.membership.application.command.suspend_membership_command;

import dev.auris.membership.domain.exception.MembershipNotFoundException;
import dev.auris.membership.domain.port.out.LoadMembershipPort;
import dev.auris.membership.domain.port.out.SaveMembershipPort;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuspendMembershipHandler {
    private final LoadMembershipPort loadMembershipPort;
    private final SaveMembershipPort saveMembershipPort;

    public SuspendMembershipHandler(LoadMembershipPort loadMembershipPort, SaveMembershipPort saveMembershipPort) {
        this.loadMembershipPort = loadMembershipPort;
        this.saveMembershipPort = saveMembershipPort;
    }

    @Transactional
    public SuspendMembershipResponse handle(SuspendMembershipCommand command) {
        var actorId = UserAccountId.fromString(command.actorUserAccountId());
        var targetId = UserAccountId.fromString(command.targetUserAccountId());
        var organizationId = OrganizationId.fromString(command.organizationId());

        var actor = loadMembershipPort.loadByUserAndOrganization(actorId, organizationId)
                .orElseThrow(() -> MembershipNotFoundException.forUser(actorId, organizationId));

        var target = loadMembershipPort.loadByUserAndOrganization(targetId, organizationId)
                .orElseThrow(() -> MembershipNotFoundException.forUser(targetId, organizationId));

        target.suspend(actor);
        saveMembershipPort.save(target);
        return SuspendMembershipResponse.from(target);

    }


}
