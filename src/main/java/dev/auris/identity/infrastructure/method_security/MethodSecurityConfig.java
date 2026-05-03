package dev.auris.identity.infrastructure.method_security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * PRIMARY authorization control layer for the application.
 * <p>
 * Web security (SecurityFilterChain) is coarse-grained: authenticated or not.
 * @PreAuthorize / @PostAuthorize at method boundaries enforce fine-grained role
 * and permission policy, regardless of transport (form-login session or JWT bearer).
 */
@Configuration
@EnableMethodSecurity(proxyTargetClass = true)
public class MethodSecurityConfig {
}
