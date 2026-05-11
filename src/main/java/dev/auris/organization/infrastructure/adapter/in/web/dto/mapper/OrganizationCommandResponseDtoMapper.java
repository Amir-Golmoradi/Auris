package dev.auris.organization.infrastructure.adapter.in.web.dto.mapper;

import dev.auris.organization.application.command.archive_organization.ArchiveOrganizationResponse;
import dev.auris.organization.application.command.create_organization.CreateOrganizationResponse;
import dev.auris.organization.application.command.deactivate_organization.DeActivateOrganizationResponse;
import dev.auris.organization.application.command.generate_customer_invitation_code.GenerateCustomerInvitationCodeResponse;
import dev.auris.organization.application.command.generate_member_invitation_code.GenerateMemberInvitationCodeResponse;
import dev.auris.organization.application.command.reactivate_organization.ReactivateOrganizationResponse;
import dev.auris.organization.application.command.update_organization_name.UpdateOrganizationNameResponse;
import dev.auris.organization.application.command.update_organization_office_phone.UpdateOrganizationOfficePhoneResponse;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.ArchiveOrganizationResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.CreateOrganizationResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.DeActivateOrganizationResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.GenerateInvitationCodeResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.ReactivateOrganizationResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.UpdateOrganizationNameResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.UpdateOrganizationOfficePhoneResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationCommandResponseDtoMapper {
    public CreateOrganizationResponseDto toDto(CreateOrganizationResponse response) {
        return new CreateOrganizationResponseDto(
                response.organizationId(),
                response.name(),
                response.officePhoneNumber(),
                response.enabled(),
                response.createdAt()
        );
    }

    public UpdateOrganizationNameResponseDto toDto(UpdateOrganizationNameResponse response) {
        return new UpdateOrganizationNameResponseDto(
                response.organizationId(),
                response.name()
        );
    }

    public UpdateOrganizationOfficePhoneResponseDto toDto(UpdateOrganizationOfficePhoneResponse response) {
        return new UpdateOrganizationOfficePhoneResponseDto(
                response.organizationId(),
                response.officePhoneNumber()
        );
    }

    public GenerateInvitationCodeResponseDto toDto(GenerateMemberInvitationCodeResponse response) {
        return new GenerateInvitationCodeResponseDto(
                response.organizationId(),
                response.invitationCode(),
                response.invitationCodeType()
        );
    }

    public GenerateInvitationCodeResponseDto toDto(GenerateCustomerInvitationCodeResponse response) {
        return new GenerateInvitationCodeResponseDto(
                response.organizationId(),
                response.invitationCode(),
                response.invitationCodeType()
        );
    }

    public ArchiveOrganizationResponseDto toDto(ArchiveOrganizationResponse response) {
        return new ArchiveOrganizationResponseDto(
                response.organizationId(),
                response.enabled()
        );
    }

    public ReactivateOrganizationResponseDto toDto(ReactivateOrganizationResponse response) {
        return new ReactivateOrganizationResponseDto(
                response.organizationId(),
                response.enabled()
        );
    }

    public DeActivateOrganizationResponseDto toDto(DeActivateOrganizationResponse response) {
        return new DeActivateOrganizationResponseDto(
                response.organizationId(),
                response.enabled()
        );
    }
}
