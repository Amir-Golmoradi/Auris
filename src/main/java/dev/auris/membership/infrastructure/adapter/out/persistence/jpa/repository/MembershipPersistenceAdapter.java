package dev.auris.membership.infrastructure.adapter.out.persistence.jpa.repository;

import dev.auris.membership.domain.enums.MembershipStatus;
import dev.auris.membership.domain.model.Membership;
import dev.auris.membership.domain.port.out.LoadMembershipPort;
import dev.auris.membership.domain.port.out.SaveMembershipPort;
import dev.auris.membership.domain.value_object.MembershipId;
import dev.auris.membership.infrastructure.adapter.out.persistence.mapper.MembershipAggregateMapper;
import dev.auris.organization.domain.value_object.OrganizationId;
import dev.auris.user_account.domain.value_object.UserAccountId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MembershipPersistenceAdapter implements LoadMembershipPort, SaveMembershipPort {
    private final MembershipAggregateMapper mapper;
    private final MembershipJpaRepository jpaRepository;

    public MembershipPersistenceAdapter(MembershipAggregateMapper mapper, MembershipJpaRepository jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Membership> loadById(MembershipId membershipId) {
        return jpaRepository.findById(membershipId.getValue()).map(mapper::toDomain);
    }

    @Override
    public List<Membership> loadAllActiveByUserId(UserAccountId userAccountId) {
        return jpaRepository.findAllByUserAccountIdAndStatus(userAccountId.getValue(), MembershipStatus.ACTIVE)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Membership> loadAllByOrganizationId(OrganizationId organizationId) {
        return jpaRepository.findAllByOrganizationId(organizationId.getValue())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Membership> loadByUserAndOrganization(UserAccountId userAccountId, OrganizationId organizationId) {
        return jpaRepository.findByUserAccountIdAndOrganizationId(userAccountId.getValue(), organizationId.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Membership save(Membership membership) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(membership)));
    }

    @Override
    public void saveAll(List<Membership> memberships) {
        jpaRepository.saveAll(memberships.stream().map(mapper::toEntity).toList());
    }
}
