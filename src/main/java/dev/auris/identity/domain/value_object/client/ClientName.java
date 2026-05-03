package dev.auris.identity.domain.value_object.client;

import org.springframework.util.Assert;

public record ClientName(String value) {
    public ClientName {
        Assert.hasText(value, "client name must not be empty");
    }
}
