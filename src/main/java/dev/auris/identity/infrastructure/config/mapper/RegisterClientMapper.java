package dev.auris.identity.infrastructure.config.mapper;

import dev.auris.identity.infrastructure.exception.InvalidRegisteredClientException;
import dev.auris.identity.infrastructure.persistance.model.RegisteredClientEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public final class RegisterClientMapper {

    public RegisteredClientEntity toEntity(RegisteredClient client) {
        if (Objects.isNull(client)) {
            throw new InvalidRegisteredClientException();
        }

        Set<String> clientAuthMethods = client.getClientAuthenticationMethods().stream()
                .map(ClientAuthenticationMethod::getValue)
                .collect(Collectors.toSet());

        Set<String> authGrantType = client.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.toSet());

        return RegisteredClientEntity.builder()
                .id(UUID.fromString(client.getId()))
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(clientAuthMethods)
                .authorizationGrantTypes(authGrantType)
                .redirectUris(client.getRedirectUris())
                .requireConsent(false)
                .build();
    }

    public RegisteredClient toClient(RegisteredClientEntity entity) {
        if (Objects.isNull(entity)) {
            throw new InvalidRegisteredClientException();
        }

        Set<ClientAuthenticationMethod> clientAuthMethods = entity.getClientAuthenticationMethods().stream()
                .map(ClientAuthenticationMethod::new)
                .collect(Collectors.toSet());

        Set<AuthorizationGrantType> authGrantType = entity.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::new)
                .collect(Collectors.toSet());


        return RegisteredClient.withId(entity.getId().toString())
                .clientId(entity.getClientId())
                .clientName(entity.getClientName())
                .clientSecret(entity.getClientSecret())
                .clientAuthenticationMethods(methods -> methods.addAll(clientAuthMethods))
                .authorizationGrantTypes(grantTypes -> grantTypes.addAll(authGrantType))
                .redirectUris(uris -> uris.addAll(entity.getRedirectUris()))
                .scopes(scopes -> scopes.addAll(entity.getScope()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(entity.isRequireConsent()).build())
                .build();
    }
}