package dev.auris.membership.application.command.add_member_command;

import dev.auris.membership.domain.enums.MembershipRole;
import dev.auris.membership.domain.model.Membership;
import dev.auris.membership.domain.policy.UniqueOrganizationMembershipPolicy;
import dev.auris.membership.domain.port.out.LoadMembershipPort;
import dev.auris.membership.domain.port.out.SaveMembershipPort;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddMemberHandler {
    private final LoadMembershipPort loadMembershipPort;
    private final SaveMembershipPort saveMembershipPort;

    public AddMemberHandler(LoadMembershipPort loadMembershipPort, SaveMembershipPort saveMembershipPort) {
        this.loadMembershipPort = loadMembershipPort;
        this.saveMembershipPort = saveMembershipPort;
    }

    @Transactional
    public AddMemberResponse handle(AddMemberCommand command) {

        var userAccountId = UserAccountId.fromString(command.userAccountId());
        var organizationId = OrganizationId.fromString(command.organizationId());
        new UniqueOrganizationMembershipPolicy(organizationId).enforce(userAccountId, loadMembershipPort);
        var membership = Membership.create(
                MembershipId.generate(),
                userAccountId,
                organizationId,
                MembershipRole.MEMBER
        );
        var newMembership = saveMembershipPort.save(membership);
        return AddMemberResponse.from(newMembership);
    }
}
