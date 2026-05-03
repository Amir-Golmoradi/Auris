package dev.auris.identity.infrastructure.config.utils;

import java.util.Set;

public final class Constants {
    /**
     * Vault Transit key name for OAuth JWT signing.
     */
    public static final String VAULT_TRANSIT_KEY_NAME = "transit/keys/auris-oauth-transit-key";
    public static final String VAULT_HEALTH_CHECK = "sys/health";

    public static final String OAUTH_SCHEME_NAME = "auris-secure-endpoint";
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-oauth2-redirect"
    };
    public static final String CLIENT_ID = "auris-client";

    public static final Set<String> CLIENT_AUTH_METHOD = Set.of("client_secret_basic");
    //    public static final Set<ClientAuthenticationMethod> CLIENT_AUTH_METHOD = Set.of(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
    public static final Set<String> AUTH_GRANT_TYPE = Set.of("authorization_code", "refresh_token");
    //    public static final Set<AuthorizationGrantType> AUTH_GRANT_TYPE = Set.of(AuthorizationGrantType.AUTHORIZATION_CODE, AuthorizationGrantType.REFRESH_TOKEN);
    public static final String AUTHORIZATION_URI = "http://localhost:8340/oauth2/authorize";
    public static final String TOKEN_URI = "http://localhost:8340/oauth2/token";
    public static final String USER_INFO_URI = "http://localhost:8340/userinfo";
    public static final String JWK_SET_URI = "http://localhost:8340/oauth2/jwks";
    public static final Set<String> REDIRECT_URI = Set.of(
            // Default Spring Client callback
            STR."http://localhost:8340/login/oauth2/code/\{CLIENT_ID}",
            // Add Swagger UI callback
            "http://localhost:8340/swagger-ui/oauth2-redirect.html" // REQUIRED for Swagger UI
    );
}
