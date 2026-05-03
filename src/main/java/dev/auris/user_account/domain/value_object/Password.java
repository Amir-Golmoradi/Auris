package dev.auris.user_account.domain.value_object;

import dev.auris.user_account.domain.exception.UserAccountValidationException;
import dev.auris.organization.domain.service.PasswordHasher;
import dev.auris.shared.building_block.ValueObject;

import java.util.List;
import java.util.regex.Pattern;


public final class Password extends ValueObject {
    private static final Pattern PASSWORD_VALIDATOR = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,24}$"
    );
    private final String value;
    private Password(String value) {
        this.value = value;
    }

    public static Password of(String value, PasswordHasher hasher) {
        validate(value);
        return new Password(hasher.hash(value));
    }

    // Only for loading from DB
    public static Password ofHashed(String hashedValue) {
        if (hashedValue == null || hashedValue.isBlank())
            throw UserAccountValidationException.invalidPassword("Hashed password cannot be empty");
        return new Password(hashedValue);
    }

    private static void validate(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw UserAccountValidationException.invalidPassword("Password cannot be empty");
        }
        if (!PASSWORD_VALIDATOR.matcher(rawValue).matches()) {
            throw UserAccountValidationException.invalidPassword("Password is not valid");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public List<Object> getAtomicValues() {
        return List.of(value);
    }

}
