package dev.auris.organization.application.dto.mapper;

import dev.auris.organization.application.dto.response.OrganizationResponseDto;
import dev.auris.organization.domain.model.Organization;
import org.springframework.stereotype.Service;

@Service
public class OrganizationDtoMapper {

    public OrganizationResponseDto apply(Organization organization) {
        return OrganizationResponseDto.create(
                organization.getName().getValue(),
                organization.getOfficePhoneNumber().getNumber(),
                organization.getInvitationCode()
        );
    }
}
