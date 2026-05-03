package dev.auris.identity.infrastructure.persistance.repository;

import dev.auris.identity.domain.repository.RegisteredClientEntityRepository;
import dev.auris.identity.infrastructure.config.mapper.RegisterClientMapper;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class RegisteredClientJpaRepository implements RegisteredClientRepository {

    private final RegisterClientMapper registerClientMapper;
    private final RegisteredClientEntityRepository clientEntityRepository;

    public RegisteredClientJpaRepository(RegisterClientMapper registerClientMapper, RegisteredClientEntityRepository clientEntityRepository) {
        this.registerClientMapper = registerClientMapper;
        this.clientEntityRepository = clientEntityRepository;
    }

    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        clientEntityRepository.save(registerClientMapper.toEntity(registeredClient));
    }

    @Override
    @Transactional(readOnly = true)
    public RegisteredClient findById(String id) {
        return clientEntityRepository.findById(UUID.fromString(id))
                .map(registerClientMapper::toClient)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public RegisteredClient findByClientId(String clientId) {
        return clientEntityRepository.findByClientId(clientId)
                .map(registerClientMapper::toClient)
                .orElse(null);
    }
}
