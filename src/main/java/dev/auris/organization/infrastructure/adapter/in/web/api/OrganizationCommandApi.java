package dev.auris.organization.infrastructure.adapter.in.web.api;

import dev.auris.organization.application.command.archive_organization.ArchiveOrganizationCommand;
import dev.auris.organization.application.command.archive_organization.ArchiveOrganizationHandler;
import dev.auris.organization.application.command.create_organization.CreateOrganizationHandler;
import dev.auris.organization.application.command.deactivate_organization.DeActivateOrganizationCommand;
import dev.auris.organization.application.command.deactivate_organization.DeActivateOrganizationHandler;
import dev.auris.organization.application.command.generate_customer_invitation_code.GenerateCustomerInvitationCodeCommand;
import dev.auris.organization.application.command.generate_customer_invitation_code.GenerateCustomerInvitationCodeHandler;
import dev.auris.organization.application.command.generate_member_invitation_code.GenerateMemberInvitationCodeCommand;
import dev.auris.organization.application.command.generate_member_invitation_code.GenerateMemberInvitationCodeHandler;
import dev.auris.organization.application.command.reactivate_organization.ReactivateOrganizationCommand;
import dev.auris.organization.application.command.reactivate_organization.ReactivateOrganizationHandler;
import dev.auris.organization.application.command.update_organization_name.UpdateOrganizationNameCommand;
import dev.auris.organization.application.command.update_organization_name.UpdateOrganizationNameHandler;
import dev.auris.organization.application.command.update_organization_office_phone.UpdateOrganizationOfficePhoneCommand;
import dev.auris.organization.application.command.update_organization_office_phone.UpdateOrganizationOfficePhoneHandler;
import dev.auris.organization.infrastructure.adapter.in.web.dto.mapper.CreateOrganizationCommandMapper;
import dev.auris.organization.infrastructure.adapter.in.web.dto.mapper.OrganizationCommandResponseDtoMapper;
import dev.auris.organization.infrastructure.adapter.in.web.dto.request.CreateOrganizationRequest;
import dev.auris.organization.infrastructure.adapter.in.web.dto.request.UpdateOrganizationNameRequest;
import dev.auris.organization.infrastructure.adapter.in.web.dto.request.UpdateOrganizationOfficePhoneRequest;
import dev.auris.organization.infrastructure.adapter.in.web.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations")
@Tag(name = "Organization", description = "Organization command endpoints")
public class OrganizationCommandApi {
    private final CreateOrganizationHandler createOrganizationHandler;
    private final UpdateOrganizationNameHandler updateOrganizationNameHandler;
    private final UpdateOrganizationOfficePhoneHandler updateOrganizationOfficePhoneHandler;
    private final GenerateMemberInvitationCodeHandler generateMemberInvitationCodeHandler;
    private final GenerateCustomerInvitationCodeHandler generateCustomerInvitationCodeHandler;
    private final ArchiveOrganizationHandler archiveOrganizationHandler;
    private final ReactivateOrganizationHandler reactivateOrganizationHandler;
    private final DeActivateOrganizationHandler deActivateOrganizationHandler;
    private final CreateOrganizationCommandMapper createOrganizationCommandMapper;
    private final OrganizationCommandResponseDtoMapper responseDtoMapper;

    public OrganizationCommandApi(
            CreateOrganizationHandler createOrganizationHandler,
            UpdateOrganizationNameHandler updateOrganizationNameHandler,
            UpdateOrganizationOfficePhoneHandler updateOrganizationOfficePhoneHandler,
            GenerateMemberInvitationCodeHandler generateMemberInvitationCodeHandler,
            GenerateCustomerInvitationCodeHandler generateCustomerInvitationCodeHandler,
            ArchiveOrganizationHandler archiveOrganizationHandler,
            ReactivateOrganizationHandler reactivateOrganizationHandler,
            DeActivateOrganizationHandler deActivateOrganizationHandler,
            CreateOrganizationCommandMapper createOrganizationCommandMapper,
            OrganizationCommandResponseDtoMapper responseDtoMapper
    ) {
        this.createOrganizationHandler = createOrganizationHandler;
        this.updateOrganizationNameHandler = updateOrganizationNameHandler;
        this.updateOrganizationOfficePhoneHandler = updateOrganizationOfficePhoneHandler;
        this.generateMemberInvitationCodeHandler = generateMemberInvitationCodeHandler;
        this.generateCustomerInvitationCodeHandler = generateCustomerInvitationCodeHandler;
        this.archiveOrganizationHandler = archiveOrganizationHandler;
        this.reactivateOrganizationHandler = reactivateOrganizationHandler;
        this.deActivateOrganizationHandler = deActivateOrganizationHandler;
        this.createOrganizationCommandMapper = createOrganizationCommandMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @PostMapping
    @Operation(summary = "Create organization")
    public ResponseEntity<CreateOrganizationResponseDto> createOrganization(
            @Valid @RequestBody CreateOrganizationRequest request
    ) {
        var command = createOrganizationCommandMapper.apply(request);
        var response = createOrganizationHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDtoMapper.toDto(response));
    }

    @PatchMapping("/{organizationId}/name")
    @PreAuthorize("hasAuthority('OWNER')")
    @Operation(summary = "Update organization name")
    public ResponseEntity<UpdateOrganizationNameResponseDto> updateName(
            @PathVariable String organizationId,
            @Valid @RequestBody UpdateOrganizationNameRequest request
    ) {
        var command = new UpdateOrganizationNameCommand(organizationId, request.newName());
        var response = updateOrganizationNameHandler.handle(command);
        return ResponseEntity.ok(responseDtoMapper.toDto(response));
    }

    @PatchMapping("/{organizationId}/office-phone")
    @PreAuthorize("hasAuthority('OWNER')")
    @Operation(summary = "Update organization office phone")
    public ResponseEntity<UpdateOrganizationOfficePhoneResponseDto> updateOfficePhone(
            @PathVariable String organizationId,
            @Valid @RequestBody UpdateOrganizationOfficePhoneRequest request
    ) {
        var command = new UpdateOrganizationOfficePhoneCommand(organizationId, request.newOfficePhoneNumber());
        var response = updateOrganizationOfficePhoneHandler.handle(command);
        return ResponseEntity.ok(responseDtoMapper.toDto(response));
    }

    @PostMapping("/{organizationId}/member-invitation-code")
    @PreAuthorize("hasAuthority('OWNER')")
    @Operation(summary = "Generate organization member invitation code")
    public ResponseEntity<GenerateInvitationCodeResponseDto> generateMemberInvitationCode(
            @PathVariable String organizationId
    ) {
        var command = new GenerateMemberInvitationCodeCommand(organizationId);
        var response = generateMemberInvitationCodeHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDtoMapper.toDto(response));
    }

    @PostMapping("/{organizationId}/customer-invitation-code")
    @PreAuthorize("hasAuthority('OWNER')")
    @Operation(summary = "Generate organization customer invitation code")
    public ResponseEntity<GenerateInvitationCodeResponseDto> generateCustomerInvitationCode(
            @PathVariable String organizationId
    ) {
        var command = new GenerateCustomerInvitationCodeCommand(organizationId);
        var response = generateCustomerInvitationCodeHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDtoMapper.toDto(response));
    }

    @PatchMapping("/{organizationId}/archive")
    @PreAuthorize("hasAuthority('OWNER')")
    @Operation(summary = "Archive organization")
    public ResponseEntity<ArchiveOrganizationResponseDto> archive(
            @PathVariable String organizationId
    ) {
        var command = new ArchiveOrganizationCommand(organizationId);
        var response = archiveOrganizationHandler.handle(command);
        return ResponseEntity.ok(responseDtoMapper.toDto(response));
    }

    @PatchMapping("/{organizationId}/reactivate")
    @PreAuthorize("hasAuthority('OWNER')")
    @Operation(summary = "Reactivate organization")
    public ResponseEntity<ReactivateOrganizationResponseDto> reactivate(
            @PathVariable String organizationId
    ) {
        var response = reactivateOrganizationHandler.handle(new ReactivateOrganizationCommand(organizationId));
        return ResponseEntity.ok(responseDtoMapper.toDto(response));
    }

    @PatchMapping("/{organizationId}/deactivate")
    @PreAuthorize("hasAuthority('OWNER')")
    @Operation(summary = "Deactivate organization")
    public ResponseEntity<DeActivateOrganizationResponseDto> deactivate(
            @PathVariable String organizationId
    ) {
        var response = deActivateOrganizationHandler.handle(new DeActivateOrganizationCommand(organizationId));
        return ResponseEntity.ok(responseDtoMapper.toDto(response));
    }
}
