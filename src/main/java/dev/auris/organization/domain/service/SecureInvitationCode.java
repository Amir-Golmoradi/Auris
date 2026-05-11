package dev.auris.organization.domain.service;

import dev.auris.organization.domain.value_object.InvitationCode;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
public final class SecureInvitationCode implements InvitationCodeGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public InvitationCode generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = RANDOM.nextInt(BASE62.length());
            sb.append(BASE62.charAt(idx));
        }
        return InvitationCode.of(sb.toString());
    }
}