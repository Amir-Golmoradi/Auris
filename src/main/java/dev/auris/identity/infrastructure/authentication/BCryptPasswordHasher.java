package dev.auris.identity.infrastructure.authentication;

import dev.auris.organization.domain.service.PasswordHasher;
import org.springframework.stereotype.Service;

@Service
public class BCryptPasswordHasher implements PasswordHasher {
    private final PasswordEncoderService encoder;

    public BCryptPasswordHasher(PasswordEncoderService encoder) {
        this.encoder = encoder;
    }

    @Override
    public String hash(String rawPassword) {
        return encoder.passwordEncoder().encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.passwordEncoder().matches(rawPassword, hashedPassword);
    }
}
