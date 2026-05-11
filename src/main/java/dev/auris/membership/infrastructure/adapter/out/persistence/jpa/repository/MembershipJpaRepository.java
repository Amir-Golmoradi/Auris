package dev.auris.membership.infrastructure.adapter.out.persistence.jpa.repository;

import dev.auris.membership.domain.enums.MembershipStatus;
import dev.auris.membership.infrastructure.adapter.out.persistence.jpa.model.MembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembershipJpaRepository extends JpaRepository<MembershipJpaEntity, UUID> {
    Optional<MembershipJpaEntity> findByUserAccountIdAndOrganizationId(UUID userAccountId, UUID organizationId);

    List<MembershipJpaEntity> findAllByUserAccountIdAndStatus(UUID userAccountId, MembershipStatus status);

    List<MembershipJpaEntity> findAllByOrganizationId(UUID organizationId);
}
