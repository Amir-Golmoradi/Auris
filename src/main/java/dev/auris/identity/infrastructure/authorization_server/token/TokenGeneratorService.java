package dev.auris.identity.infrastructure.authorization_server.token;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Service;

@Service
public class TokenGeneratorService {
    private final OAuthAccessTokenCustomizer OAuthAccessTokenCustomizer;

    public TokenGeneratorService(OAuthAccessTokenCustomizer OAuthAccessTokenCustomizer) {
        this.OAuthAccessTokenCustomizer = OAuthAccessTokenCustomizer;
    }

    public OAuth2TokenGenerator<OAuth2Token> createTokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);
        JwtGenerator jwtAccessTokenGenerator = new JwtGenerator(jwtEncoder);
        jwtAccessTokenGenerator.setJwtCustomizer(OAuthAccessTokenCustomizer);
        return new DelegatingOAuth2TokenGenerator(jwtAccessTokenGenerator);
    }
}