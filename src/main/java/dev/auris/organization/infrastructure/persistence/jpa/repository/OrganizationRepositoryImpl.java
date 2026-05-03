package dev.auris.organization.infrastructure.persistence.jpa.repository;

import dev.auris.organization.domain.model.Organization;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.infrastructure.mapper.OrganizationAggregateMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrganizationRepositoryImpl implements LoadOrganizationPort, SaveOrganizationPort {
    private final OrganizationAggregateMapper aggregateMapper;
    private final OrganizationJpaRepository organizationJpaRepository;

    public OrganizationRepositoryImpl(OrganizationAggregateMapper aggregateMapper, OrganizationJpaRepository organizationJpaRepository) {
        this.aggregateMapper = aggregateMapper;
        this.organizationJpaRepository = organizationJpaRepository;
    }

    @Override
    public Optional<Organization> loadByOrganizationId(String OrganizationId) {
        return organizationJpaRepository
                .findByName(OrganizationId)
                .map(aggregateMapper::apply);
    }

    @Override
    public Organization save(Organization organization) {
        var OrganizationEntity = aggregateMapper.toEntity(organization);
        var savedEntity = organizationJpaRepository.save(OrganizationEntity);
        return aggregateMapper.apply(savedEntity);
    }
}
