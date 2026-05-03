package dev.auris.organization.application.service;

import dev.auris.organization.domain.exception.InvitationCodeNotFoundException;
import dev.auris.organization.domain.model.Organization;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.domain.service.InvitationCodeGenerator;
import dev.auris.organization.domain.value_object.OrganizationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrganizationServiceImpl implements OrganizationService {
    private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    private final LoadOrganizationPort loadOrganizationPort;
    private final SaveOrganizationPort saveOrganizationPort;
    private final InvitationCodeGenerator codeGenerator;

    public OrganizationServiceImpl(LoadOrganizationPort loadOrganizationPort, SaveOrganizationPort saveOrganizationPort, InvitationCodeGenerator codeGenerator) {
        this.loadOrganizationPort = loadOrganizationPort;
        this.saveOrganizationPort = saveOrganizationPort;
        this.codeGenerator = codeGenerator;
    }

    @Override
    @Transactional
    public String generateInvitationCode(OrganizationId organizationId) {
        Organization organization = loadOrganizationPort.loadByOrganizationId(organizationId.getValue().toString())
                .orElseThrow(() -> new IllegalArgumentException(STR."Organization not found for id: \{organizationId}"));
        organization.assignInvitationCode(codeGenerator);
        saveOrganizationPort.save(organization);
        log.info("Assigned new invitation code for organization: {}", organizationId);
        return organization.getInvitationCode();
    }

    @Override
    public String readInvitationCode(OrganizationId organizationId) {
        var organization = loadOrganizationPort
                .loadByOrganizationId(organizationId.getValue().toString())
                .orElseThrow(InvitationCodeNotFoundException::new);
        return organization.getInvitationCode();
    }
}
