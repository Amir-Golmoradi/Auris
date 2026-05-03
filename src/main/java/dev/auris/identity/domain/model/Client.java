package dev.auris.identity.domain.model;

import dev.auris.identity.domain.enums.client.ClientAuthMethod;
import dev.auris.identity.domain.enums.client.GrantType;
import dev.auris.shared.building_block.AggregateRoot;
import dev.auris.shared.building_block.DomainEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Client extends AggregateRoot<UUID, DomainEvent> {

    private String clientId;
    private String clientName;
    private String clientSecret;
    private Set<ClientAuthMethod> clientAuthenticationMethods = new HashSet<>();
    private Set<GrantType> authorizationGrantTypes = new HashSet<>();
    private Set<String> redirectUris;
    private Set<String> scopes;
    private boolean requireConsent;

    private Client(UUID id, String clientId, String clientName, String clientSecret, Set<String> redirectUris, Set<String> scopes, boolean requireConsent) {
        super(id);
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientSecret = clientSecret;
        this.redirectUris = redirectUris;
        this.scopes = scopes;
        this.requireConsent = requireConsent;
    }

    public static Client of(
            String clientId,
            String clientName,
            String clientSecret
    ) {
        return new Client(
                UUID.randomUUID(),
                clientId,
                clientName,
                clientSecret,
                new HashSet<>(),
                new HashSet<>(),
                false
        );
    }

    public String getClientId() {
        return clientId;
    }
    public Set<String> getScopes() {
        return scopes;
    }
    public String getClientName() {
        return clientName;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public boolean isRequireConsent() {
        return requireConsent;
    }
    public Set<String> getRedirectUris() {
        return redirectUris;
    }
    public Set<GrantType> getAuthorizationGrantTypes() {
        return authorizationGrantTypes;
    }
    public Set<ClientAuthMethod> getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }
}

