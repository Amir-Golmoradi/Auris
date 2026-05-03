package dev.auris.identity.domain.value_object.client;

import java.util.regex.Pattern;

public record ClientSecret(String value) {
    private static final Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*\\S{8,}$");

    public ClientSecret {
        if (value == null || !pattern.matcher(value).matches()) {
            throw new IllegalArgumentException("clientSecret is too weak");
        }
    }
}
