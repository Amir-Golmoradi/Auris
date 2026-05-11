package dev.auris.shared.config;

import dev.auris.identity.infrastructure.config.utils.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    private static final String API_VERSION = "v1";

    @Bean
    public OpenAPI aurisOpenApi() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8340")
                                .description("Local Auris API")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Auris API documentation")
                        .url("/swagger-ui/index.html"))
                .tags(List.of(
                        new Tag()
                                .name("Identity")
                                .description("OAuth2 authorization server, token, and security diagnostic endpoints"),
                        new Tag()
                                .name("User Account")
                                .description("User account command and query APIs"),
                        new Tag()
                                .name("Organization")
                                .description("Organization command and query APIs, including invitation-code operations"),
                        new Tag()
                                .name("Membership")
                                .description("Organization membership lifecycle and role-management APIs")
                ))
                .components(new Components()
                        .addSecuritySchemes(Constants.OAUTH_SCHEME_NAME, oauth2AuthorizationCodeScheme()))
                .addSecurityItem(new SecurityRequirement().addList(Constants.OAUTH_SCHEME_NAME));
    }

    @Bean
    public GroupedOpenApi identityOpenApi() {
        return GroupedOpenApi.builder()
                .group("identity")
                .pathsToMatch(
                        "/oauth2/**",
                        "/login/**",
                        "/userinfo",
                        "/member",
                        "/owner"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi userAccountOpenApi() {
        return GroupedOpenApi.builder()
                .group("user-account")
                .pathsToMatch("/api/v1/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi organizationOpenApi() {
        return GroupedOpenApi.builder()
                .group("organization")
                .pathsToMatch("/api/v1/organizations/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicOpenApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch("/**")
                .pathsToExclude(
                        "/actuator/**",
                        "/error"
                )
                .build();
    }

    private Info apiInfo() {
        return new Info()
                .title("Auris Loyalty Customer API")
                .version(API_VERSION)
                .description("""
                        DDD and hexagonal Spring Boot API for Auris loyalty, identity, organization, user-account, and membership workflows.
                        """)
                .contact(new Contact()
                        .name("Auris Engineering")
                        .url("https://auris.dev")
                        .email("engineering@auris.dev"))
                .license(new License()
                        .name("Proprietary")
                        .url("https://auris.dev"));
    }

    private SecurityScheme oauth2AuthorizationCodeScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .description("OAuth2 authorization-code flow issued by Auris Authorization Server")
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl(Constants.AUTHORIZATION_URI)
                                .tokenUrl(Constants.TOKEN_URI)
                                .scopes(new Scopes()
                                        .addString("openid", "OpenID Connect identity scope")
                                        .addString("profile", "User profile scope")
                                        .addString("refresh_token", "Refresh token support"))));
    }
}
