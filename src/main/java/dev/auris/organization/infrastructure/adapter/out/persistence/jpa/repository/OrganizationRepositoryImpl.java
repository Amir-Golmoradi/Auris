package dev.auris.organization.infrastructure.adapter.out.persistence.jpa.repository;

import dev.auris.organization.domain.model.Organization;
import dev.auris.organization.domain.port.out.LoadOrganizationPort;
import dev.auris.organization.domain.port.out.SaveOrganizationPort;
import dev.auris.organization.infrastructure.adapter.out.persistence.mapper.OrganizationAggregateMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrganizationRepositoryImpl implements LoadOrganizationPort, SaveOrganizationPort {
    private final OrganizationAggregateMapper aggregateMapper;
    private final OrganizationJpaRepository organizationJpaRepository;

    public OrganizationRepositoryImpl(OrganizationAggregateMapper aggregateMapper, OrganizationJpaRepository organizationJpaRepository) {
        this.aggregateMapper = aggregateMapper;
        this.organizationJpaRepository = organizationJpaRepository;
    }

    @Override
    public Optional<Organization> loadByOrganizationId(String organizationId) {
        return organizationJpaRepository
                .findById(UUID.fromString(organizationId))
                .map(aggregateMapper::apply);
    }

    @Override
    public Optional<Organization> loadByName(String name) {
        return organizationJpaRepository
                .findByName(name)
                .map(aggregateMapper::apply);
    }

    @Override
    public List<Organization> loadAll() {
        return organizationJpaRepository.findAll()
                .stream()
                .map(aggregateMapper::apply)
                .toList();
    }

    @Override
    public Organization save(Organization organization) {
        var OrganizationEntity = aggregateMapper.toEntity(organization);
        var savedEntity = organizationJpaRepository.save(OrganizationEntity);
        return aggregateMapper.apply(savedEntity);
    }
}
