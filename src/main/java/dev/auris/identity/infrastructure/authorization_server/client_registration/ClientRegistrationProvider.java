package dev.auris.identity.infrastructure.authorization_server.client_registration;

import dev.auris.identity.domain.repository.RegisteredClientEntityRepository;
import dev.auris.identity.infrastructure.exception.InvalidRegisteredClientException;
import dev.auris.identity.infrastructure.persistance.model.RegisteredClientEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

import static dev.auris.identity.infrastructure.config.utils.Constants.*;

@Component
public final class ClientRegistrationProvider {
    public ClientRegistrationRepository getClientRegistrationRepository(
            RegisteredClientEntityRepository clientEntityRepository) {
        return registrationId -> {
            RegisteredClientEntity entity = clientEntityRepository
                    .findByClientId(registrationId)
                    .orElseThrow(() -> new InvalidRegisteredClientException("Unknown client: " + registrationId));
            return ClientRegistration.withRegistrationId(registrationId)
                    .clientId(entity.getClientId())
                    .clientName(entity.getClientName())
                    .clientSecret("secret")
                    .scope(entity.getScope())
                    .redirectUri(entity.getRedirectUris().iterator().next())
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationUri(AUTHORIZATION_URI)
                    .tokenUri(TOKEN_URI)
                    .userInfoUri(USER_INFO_URI)
                    .jwkSetUri(JWK_SET_URI)
                    .userNameAttributeName(IdTokenClaimNames.SUB)
                    .build();
        };
    }
}
