package dev.auris.organization.infrastructure.web;

import dev.auris.organization.application.service.OrganizationService;
import dev.auris.organization.domain.value_object.OrganizationId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Organization")
public class OrganizationApi {
    private final OrganizationService service;

    public OrganizationApi(OrganizationService service) {
        this.service = service;
    }

    @PostMapping("/invitation_code")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<String> generateInvitationCode(OrganizationId organizationId) {
        String generate = service.generateInvitationCode(organizationId);
        return ResponseEntity.ok(generate);
    }

    // READ INVITATION CODE
    @GetMapping("/invitation_code")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> showInvitationCode(OrganizationId organizationId) {
        String invitationCode = service.readInvitationCode(organizationId);
        return ResponseEntity.ok(invitationCode);
    }
}
