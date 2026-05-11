package dev.auris.organization.infrastructure.adapter.out.persistence.jpa.repository;

import dev.auris.organization.infrastructure.adapter.out.persistence.jpa.model.OrganizationJpaEntity;
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

    @Query("SELECT b.memberInvitationCode FROM OrganizationJpaEntity b WHERE b.id = :id")
    Optional<String> findInvitationCodeById(@Param("id") UUID organizationId);

    @Query("SELECT b.customerInvitationCode FROM OrganizationJpaEntity b WHERE b.id = :id")
    Optional<String> findCustomerInvitationCodeById(@Param("id") UUID organizationId);

    @Modifying
    @Query("UPDATE OrganizationJpaEntity b SET b.memberInvitationCode = :code WHERE b.id = :id")
    void updateInvitationCode(@Param("code") String code, @Param("id") UUID id);

    @Modifying
    @Query("UPDATE OrganizationJpaEntity b SET b.customerInvitationCode = :code WHERE b.id = :id")
    void updateCustomerInvitationCode(@Param("code") String code, @Param("id") UUID id);
}
