package dev.auris.organization.infrastructure.adapter.in.web.api;

import dev.auris.organization.application.query.get_organization_by_id.GetOrganizationByIdHandler;
import dev.auris.organization.application.query.get_organization_by_id.GetOrganizationByIdQuery;
import dev.auris.organization.application.query.get_organization_invitation_code.GetOrganizationInvitationCodeHandler;
import dev.auris.organization.application.query.get_organization_invitation_code.GetOrganizationInvitationCodeQuery;
import dev.auris.organization.application.query.get_organization_profile.GetOrganizationProfileHandler;
import dev.auris.organization.application.query.get_organization_profile.GetOrganizationProfileQuery;
import dev.auris.organization.application.query.list_organizations.ListOrganizationsHandler;
import dev.auris.organization.application.query.list_organizations.ListOrganizationsQuery;
import dev.auris.organization.infrastructure.adapter.in.web.dto.mapper.OrganizationQueryResponseDtoMapper;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.GetOrganizationByIdResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.GetOrganizationInvitationCodeResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.GetOrganizationProfileResponseDto;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.ListOrganizationsItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
@Tag(name = "Organization", description = "Organization query endpoints")
public class OrganizationQueryApi {
    private final GetOrganizationByIdHandler getOrganizationByIdHandler;
    private final ListOrganizationsHandler listOrganizationsHandler;
    private final GetOrganizationProfileHandler getOrganizationProfileHandler;
    private final GetOrganizationInvitationCodeHandler getOrganizationInvitationCodeHandler;
    private final OrganizationQueryResponseDtoMapper responseDtoMapper;

    public OrganizationQueryApi(
            GetOrganizationByIdHandler getOrganizationByIdHandler,
            ListOrganizationsHandler listOrganizationsHandler,
            GetOrganizationProfileHandler getOrganizationProfileHandler,
            GetOrganizationInvitationCodeHandler getOrganizationInvitationCodeHandler,
            OrganizationQueryResponseDtoMapper responseDtoMapper
    ) {
        this.getOrganizationByIdHandler = getOrganizationByIdHandler;
        this.listOrganizationsHandler = listOrganizationsHandler;
        this.getOrganizationProfileHandler = getOrganizationProfileHandler;
        this.getOrganizationInvitationCodeHandler = getOrganizationInvitationCodeHandler;
        this.responseDtoMapper = responseDtoMapper;
    }

    @GetMapping("/{organizationId}")
    @Operation(summary = "Get organization by ID")
    public GetOrganizationByIdResponseDto getById(@PathVariable String organizationId) {
        var response = getOrganizationByIdHandler.handle(new GetOrganizationByIdQuery(organizationId));
        return responseDtoMapper.toDto(response);
    }

    @GetMapping
    @Operation(summary = "List organizations")
    public List<ListOrganizationsItemDto> listOrganizations() {
        return listOrganizationsHandler.handle(new ListOrganizationsQuery()).stream()
                .map(responseDtoMapper::toDto)
                .toList();
    }

    @GetMapping("/{organizationId}/profile")
    @Operation(summary = "Get organization profile")
    public GetOrganizationProfileResponseDto getProfile(@PathVariable String organizationId) {
        var response = getOrganizationProfileHandler.handle(new GetOrganizationProfileQuery(organizationId));
        return responseDtoMapper.toDto(response);
    }

    @GetMapping("/{organizationId}/member-invitation-code")
    @Operation(summary = "Get organization member invitation code")
    public GetOrganizationInvitationCodeResponseDto getMemberInvitationCode(@PathVariable String organizationId) {
        var response = getOrganizationInvitationCodeHandler.handleMemberInvitationCode(
                new GetOrganizationInvitationCodeQuery(organizationId)
        );
        return responseDtoMapper.toDto(response);
    }

    @GetMapping("/{organizationId}/customer-invitation-code")
    @Operation(summary = "Get organization customer invitation code")
    public GetOrganizationInvitationCodeResponseDto getCustomerInvitationCode(@PathVariable String organizationId) {
        var response = getOrganizationInvitationCodeHandler.handleCustomerInvitationCode(
                new GetOrganizationInvitationCodeQuery(organizationId)
        );
        return responseDtoMapper.toDto(response);
    }
}
