package dev.auris.identity.infrastructure.authorization_server.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import dev.auris.identity.domain.repository.RegisteredClientEntityRepository;
import dev.auris.identity.infrastructure.config.utils.Constants;
import dev.auris.identity.infrastructure.authorization_server.client_registration.ClientRegistrationProvider;
import dev.auris.identity.infrastructure.authorization_server.jwk.JwkSourceProvider;
import dev.auris.identity.infrastructure.authorization_server.token.TokenGeneratorService;
import dev.auris.identity.infrastructure.authentication.PasswordEncoderService;
import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {
    private final ClientRegistrationProvider clientRegistrationProvider;
    private final TokenGeneratorService tokenGeneratorService;
    private final JwkSourceProvider jwkSourceProvider;
    private final PasswordEncoderService passwordEncoderService;

    public AuthorizationServerConfiguration(ClientRegistrationProvider clientRegistrationProvider, TokenGeneratorService tokenGeneratorService, JwkSourceProvider jwkSourceProvider, PasswordEncoderService passwordEncoderService) {
        this.clientRegistrationProvider = clientRegistrationProvider;
        this.tokenGeneratorService = tokenGeneratorService;
        this.jwkSourceProvider = jwkSourceProvider;
        this.passwordEncoderService = passwordEncoderService;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, authorizationServer -> authorizationServer
                        .tokenGenerator(tokenGenerator(jwkSource()))
                        .oidc(Customizer.withDefaults()
                        )
                )
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        http.exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        ))
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

        return http.build();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        JwtGrantedAuthoritiesConverter rolesConverter = new JwtGrantedAuthoritiesConverter();
        rolesConverter.setAuthoritiesClaimName("roles");
        rolesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(rolesConverter);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .oauth2ResourceServer(rs -> rs
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/", "/login", "/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/Organization/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/Organization/register/invitation_code").permitAll()
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        .requestMatchers(Constants.SWAGGER_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(RegisteredClientEntityRepository entityRepository) {
        return clientRegistrationProvider.getClientRegistrationRepository(entityRepository);
    }


    @Bean
    OAuth2TokenGenerator<OAuth2Token> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        return tokenGeneratorService.createTokenGenerator(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        return jwkSourceProvider.jwkSourceGenerator();
    }


    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8340")
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoderService.passwordEncoder();
    }
}
