package dev.auris.user_account.domain.value_object;

import dev.auris.user_account.domain.exception.UserAccountValidationException;
import dev.auris.shared.building_block.ValueObject;

import java.util.List;
import java.util.regex.Pattern;

public final class Email extends ValueObject {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final String value;

    private Email(String value) {
        validateEmail(value);
        this.value = value.trim();
    }

    public static Email of(String value) {
        return new Email(value);
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw UserAccountValidationException.invalidEmail("Email cannot be empty or null");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw UserAccountValidationException.invalidEmail("Email format is not valid");
        }
    }

    @Override
    public List<Object> getAtomicValues() {
        return List.of(value);
    }

    public String getValue() {
        return value;
    }
}
