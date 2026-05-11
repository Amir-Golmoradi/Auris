package dev.auris.organization.infrastructure.adapter.out.persistence.mapper;

import dev.auris.organization.domain.model.Organization;
import dev.auris.organization.domain.value_object.InvitationCode;
import dev.auris.organization.domain.value_object.OfficePhoneNumber;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.organization.domain.value_object.OrganizationName;
import dev.auris.organization.infrastructure.adapter.out.persistence.jpa.model.OrganizationJpaEntity;
import org.springframework.stereotype.Service;

@Service
public class OrganizationAggregateMapper {

    public Organization apply(OrganizationJpaEntity organizationJpaEntity) {
        return Organization.reconstruct(
                OrganizationId.fromString(organizationJpaEntity.getId().toString()),
                OrganizationName.of(organizationJpaEntity.getName()),
                OfficePhoneNumber.of(organizationJpaEntity.getOfficePhoneNumber()),
                toInvitationCode(organizationJpaEntity.getMemberInvitationCode()),
                toInvitationCode(organizationJpaEntity.getCustomerInvitationCode()),
                organizationJpaEntity.getCreatedAt(),
                Boolean.TRUE.equals(organizationJpaEntity.getEnabled()),
                null
        );
    }

    public OrganizationJpaEntity toEntity(Organization organization) {
        return OrganizationJpaEntity.create(
                organization.getId().getValue(),
                organization.getName().getValue(),
                organization.getCreatedAt(),
                organization.getOfficePhoneNumber().getNumber(),
                organization.getIsOrganizationEnabled(),
                organization.getMemberInvitationCode(),
                organization.getCustomerInvitationCode()
        );
    }

    private InvitationCode toInvitationCode(String value) {
        return value == null || value.isBlank() ? null : InvitationCode.of(value);
    }
}
