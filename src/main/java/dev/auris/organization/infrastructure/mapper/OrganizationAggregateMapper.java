package dev.auris.organization.infrastructure.mapper;

import dev.auris.organization.domain.model.Organization;
import dev.auris.organization.domain.value_object.OfficePhoneNumber;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.organization.domain.value_object.OrganizationName;
import dev.auris.organization.infrastructure.persistence.jpa.model.OrganizationJpaEntity;
import org.springframework.stereotype.Service;

@Service
public class OrganizationAggregateMapper {

    public Organization apply(OrganizationJpaEntity organizationJpaEntity) {
        return Organization.reconstruct(
                OrganizationId.fromString(organizationJpaEntity.getId().toString()),
                OrganizationName.of(organizationJpaEntity.getName()),
                OfficePhoneNumber.of(organizationJpaEntity.getOfficePhoneNumber()),
                organizationJpaEntity.getInvitationCode()
        );
    }

    public OrganizationJpaEntity toEntity(Organization organization) {
        return OrganizationJpaEntity.create(
                organization.getName().getValue(),
                organization.getOfficePhoneNumber().getNumber(),
                organization.getInvitationCode()
        );
    }
}
