package dev.auris.user_account.application.command.register_new_owner_account;

import dev.auris.membership.domain.enums.MembershipRole;
import dev.auris.membership.domain.model.Membership;
import dev.auris.membership.domain.port.out.SaveMembershipPort;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.organization.domain.model.Organization;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.domain.service.InvitationCodeGenerator;
import dev.auris.organization.domain.service.PasswordHasher;
import dev.auris.organization.domain.value_object.OfficePhoneNumber;
import dev.auris.organization.domain.value_object.OrganizationName;
import dev.auris.user_account.domain.model.UserAccount;
import dev.auris.user_account.domain.port.out.SaveUserAccountPort;
import dev.auris.user_account.domain.value_object.Email;
import dev.auris.user_account.domain.value_object.FullName;
import dev.auris.user_account.domain.value_object.Password;
import dev.auris.user_account.domain.value_object.PhoneNumber;
import dev.auris.user_account.domain.value_object.UserName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterNewOwnerHandler {
    private final PasswordHasher passwordHasher;
    private final SaveUserAccountPort saveAccountPort;
    private final InvitationCodeGenerator codeGenerator;
    private final SaveOrganizationPort saveOrganizationPort;
    private final SaveMembershipPort saveMembershipPort;


    public RegisterNewOwnerHandler(
            PasswordHasher passwordHasher,
            SaveUserAccountPort saveAccountPort,
            InvitationCodeGenerator codeGenerator,
            SaveOrganizationPort saveOrganizationPort,
            SaveMembershipPort saveMembershipPort) {
        this.passwordHasher = passwordHasher;
        this.saveAccountPort = saveAccountPort;
        this.codeGenerator = codeGenerator;
        this.saveOrganizationPort = saveOrganizationPort;
        this.saveMembershipPort = saveMembershipPort;
    }

    @Transactional
    public RegisterNewOwnerResponse handle(RegisterNewOwnerCommand command) {
        // 1. Create the organization first
        var organization = Organization.create(
                OrganizationName.of(command.organizationName()),
                OfficePhoneNumber.of(command.officePhoneNumber())
        );
        organization.assignInvitationCode(codeGenerator);
        // 2. Save the created organization
        Organization savedOrganization = saveOrganizationPort.save(organization);
        // 3. Create the user account
        var userAccount = UserAccount.registerNewOwner(
                Email.of(command.email()),
                Password.of(command.password(), passwordHasher),
                UserName.of(command.username()),
                FullName.of(command.firstName(), command.lastName()),
                PhoneNumber.of(command.phoneNumber()),
                command.description()
        );
        var savedAccount = saveAccountPort.save(userAccount);

        var membership = Membership.create(
                MembershipId.generate(),
                savedAccount.getId(),
                savedOrganization.getId(),
                MembershipRole.OWNER
        );
        saveMembershipPort.save(membership);

        return new RegisterNewOwnerResponse(
                savedAccount.getId().getValue().toString(),
                savedOrganization.getId().getValue().toString(),
                savedAccount.getEmail().getValue(),
                savedAccount.getUsername().getValue(),
                savedAccount.getFullName().getFirstName(),
                savedAccount.getFullName().getLastName(),
                savedAccount.getPhoneNumber().getValue(),
                savedOrganization.getName().getValue(),
                savedOrganization.getOfficePhoneNumber().getNumber(),
                savedAccount.getDescription()
        );
    }
}
