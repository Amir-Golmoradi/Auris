package dev.auris.identity.domain.repository;

import dev.auris.identity.infrastructure.persistance.model.RegisteredClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegisteredClientEntityRepository extends JpaRepository<RegisteredClientEntity, UUID> {
    boolean existsByClientId(String clientId);

    Optional<RegisteredClientEntity> findByClientId(String clientId);
}
