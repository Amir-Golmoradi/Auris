package dev.auris.organization.infrastructure.persistence.jpa.repository;

import dev.auris.organization.infrastructure.persistence.jpa.model.OrganizationJpaEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationJpaRepository extends JpaRepository<OrganizationJpaEntity, UUID> {
    @Query("SELECT b FROM OrganizationJpaEntity b WHERE b.name = :name")
    Optional<OrganizationJpaEntity> findByName(@Param("name") String name);

    @Query("SELECT b.invitationCode FROM OrganizationJpaEntity b WHERE b.id = :id")
    Optional<String> findInvitationCodeById(@Param("id") UUID organizationId);

    @Modifying
    @Query("UPDATE OrganizationJpaEntity b SET b.invitationCode = :code WHERE b.id = :id")
    void updateInvitationCode(@Param("code") String code, @Param("id") UUID id);
}
