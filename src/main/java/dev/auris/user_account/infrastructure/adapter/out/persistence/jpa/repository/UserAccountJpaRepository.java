package dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.repository;

import dev.auris.user_account.infrastructure.adapter.out.persistence.jpa.model.UserAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountJpaRepository extends JpaRepository<UserAccountJpaEntity, UUID> {

    @Query("SELECT u FROM UserAccountJpaEntity u WHERE u.username = :username")
    Optional<UserAccountJpaEntity> findByUsername(@Param("username") String username);

    List<UserAccountJpaEntity> findAllByOrderByCreatedAtDesc();
}
