package dev.auris.identity.infrastructure.persistance.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "oauth_client")
public class RegisteredClientEntity {
    private RegisteredClientEntity() {}

    RegisteredClientEntity(
            UUID id, String clientId, String clientName,
            String clientSecret, Set<String> clientAuthenticationMethods,
            Set<String> authorizationGrantTypes, Set<String> redirectUris, Set<String> scope,
            boolean requireConsent
    ) {
        this.id = Objects.requireNonNull(id);
        this.clientId = Objects.requireNonNull(clientId);
        this.clientName = Objects.requireNonNull(clientName);
        this.clientSecret = Objects.requireNonNull(clientSecret);
        this.clientAuthenticationMethods = Objects.requireNonNull(clientAuthenticationMethods);
        this.authorizationGrantTypes = Objects.requireNonNull(authorizationGrantTypes);
        this.redirectUris = Objects.requireNonNull(redirectUris);
        this.scope = Objects.requireNonNull(scope);
        this.requireConsent = requireConsent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "oauth_client_auth_methods",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
    )
    @Column(name = "auth_method", nullable = false)
    private Set<String> clientAuthenticationMethods = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "oauth_client_grant_types",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
    )
    @Column(name = "grant_type", nullable = false)
    private Set<String> authorizationGrantTypes = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "oauth_client_redirect_uris",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
    )
    @Column(name = "redirect_uri", nullable = false, length = 2000)
    private Set<String> redirectUris = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "oauth_client_scopes",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
    )
    @Column(name = "scope", nullable = false)
    private Set<String> scope;

    @Column(nullable = false)
    private boolean requireConsent;

    public UUID getId() {
        return id;
    }
    public Set<String> getScope() {
        return scope;
    }
    public String getClientId() {
        return clientId;
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
        return Set.copyOf(redirectUris);
    }
    public Set<String> getAuthorizationGrantTypes() {
        return Set.copyOf(authorizationGrantTypes);
    }
    public Set<String> getClientAuthenticationMethods() {
        return Set.copyOf(clientAuthenticationMethods);
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder {
        private UUID id;
        private String clientId;
        private String clientName;
        private String clientSecret;
        private boolean requireConsent;
        private Set<String> scope = new HashSet<>();
        private Set<String> redirectUris = new HashSet<>();
        private Set<String> authorizationGrantTypes = new HashSet<>();
        private Set<String> clientAuthenticationMethods = new HashSet<>();

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }
        public Builder addScope(String scope) {
            this.scope.add(scope);
            return this;
        }
        public Builder scope(Set<String> scope) {
            this.scope = scope;
            return this;
        }
        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }
        public Builder addRedirectUri(String uri) {
            this.redirectUris.add(uri);
            return this;
        }
        public Builder addAuthMethod(String method) {
            this.clientAuthenticationMethods.add(method);
            return this;
        }
        public Builder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }
        public Builder addGrantType(String grantType) {
            this.authorizationGrantTypes.add(grantType);
            return this;
        }
        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }
        public Builder redirectUris(Set<String> redirectUris) {
            this.redirectUris = redirectUris;
            return this;
        }
        public Builder requireConsent(boolean requireConsent) {
            this.requireConsent = requireConsent;
            return this;
        }
        public Builder authorizationGrantTypes(Set<String> authGrantType) {
            this.authorizationGrantTypes = authGrantType;
            return this;
        }
        public Builder clientAuthenticationMethods(Set<String> clientAuthMethod) {
            this.clientAuthenticationMethods = clientAuthMethod;
            return this;
        }

        public RegisteredClientEntity build() {
            return new RegisteredClientEntity(
                    Objects.requireNonNull(id, "id"),
                    Objects.requireNonNull(clientId, "clientId"),
                    Objects.requireNonNull(clientName, "clientName"),
                    Objects.requireNonNull(clientSecret, "clientSecret"),
                    clientAuthenticationMethods,
                    authorizationGrantTypes,
                    redirectUris,
                    scope,
                    requireConsent
            );
        }
    }
}
