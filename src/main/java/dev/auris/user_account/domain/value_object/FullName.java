package dev.auris.user_account.domain.value_object;

import dev.auris.user_account.domain.exception.UserAccountValidationException;
import dev.auris.shared.building_block.ValueObject;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public final class FullName extends ValueObject {
    private static final Pattern NAME_VALIDATOR =
            Pattern.compile("^[\\p{L}\\p{M}]+(?:[\\s\\u200c][\\p{L}\\p{M}]+)*$");

    private final String firstName;
    private final String lastName;

    private FullName(String firstName, String lastName) {
        validateName(firstName, "First Name");
        validateName(lastName, "Last Name");
        this.firstName = Optional.ofNullable(firstName).map(String::trim).orElse("");
        this.lastName = Optional.ofNullable(lastName).map(String::trim).orElse("");
    }


    public static FullName of(String firstName, String lastName) {
        return new FullName(firstName, lastName);
    }

    private void validateName(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw UserAccountValidationException.invalidFullName(fieldName + " cannot be empty");
        }

        if (value.length() < 2 || value.length() > 50) {
            throw UserAccountValidationException.invalidFullName(fieldName + " must be between 2 and 50 characters");
        }

        if (!NAME_VALIDATOR.matcher(value).matches()) {
            throw UserAccountValidationException.invalidFullName(fieldName + " contains invalid characters");
        }
    }

    @Override
    public List<Object> getAtomicValues() {
        return List.of(firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
}
