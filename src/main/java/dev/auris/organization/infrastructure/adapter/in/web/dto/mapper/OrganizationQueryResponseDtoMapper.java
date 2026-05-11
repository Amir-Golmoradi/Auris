package dev.auris.organization.infrastructure.adapter.in.web.dto.mapper;

import dev.auris.organization.application.query.get_organization_by_id.GetOrganizationByIdResponse;
import dev.auris.organization.application.query.get_organization_invitation_code.GetOrganizationInvitationCodeResponse;
import dev.auris.organization.application.query.get_organization_profile.GetOrganizationProfileResponse;
import dev.auris.organization.application.query.list_organizations.ListOrganizationsItem;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.GetOrganizationByIdResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.GetOrganizationInvitationCodeResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.GetOrganizationProfileResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.ListOrganizationsItemDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationQueryResponseDtoMapper {
    public GetOrganizationByIdResponseDto toDto(GetOrganizationByIdResponse response) {
        return new GetOrganizationByIdResponseDto(
                response.organizationId(),
                response.name(),
                response.officePhoneNumber(),
                response.enabled(),
                response.createdAt()
        );
    }

    public GetOrganizationProfileResponseDto toDto(GetOrganizationProfileResponse response) {
        return new GetOrganizationProfileResponseDto(
                response.organizationId(),
                response.name(),
                response.officePhoneNumber()
        );
    }

    public GetOrganizationInvitationCodeResponseDto toDto(GetOrganizationInvitationCodeResponse response) {
        return new GetOrganizationInvitationCodeResponseDto(
                response.organizationId(),
                response.invitationCode(),
                response.invitationCodeType()
        );
    }

    public ListOrganizationsItemDto toDto(ListOrganizationsItem item) {
        return new ListOrganizationsItemDto(
                item.organizationId(),
                item.name(),
                item.officePhoneNumber(),
                item.enabled(),
                item.createdAt()
        );
    }
}
