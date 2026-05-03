package dev.auris.identity.domain.value_object.client;

import org.springframework.util.Assert;

import java.util.UUID;

public record ClientId(UUID value) {
    public ClientId {
        Assert.notNull(value, "clientId must not be null");
    }

    public ClientId(String value) {
        this(UUID.fromString(value));
    }

    public static ClientId generate() {
        return new ClientId(UUID.randomUUID());
    }
}
