package dev.auris.user_account.domain.value_object;

import dev.auris.user_account.domain.exception.UserAccountValidationException;
import dev.auris.shared.building_block.ValueObject;

import java.util.List;
import java.util.regex.Pattern;


public final class PhoneNumber extends ValueObject {
    private static final Pattern VALIDATOR = Pattern.compile("^\\+[1-9]\\d{6,14}$");
    private final String value;

    private PhoneNumber(String value) {
        validate(value);
        this.value = value.replaceAll("\\s+", "");
    }

    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }

    public String getValue() {
        return value;
    }

    private void validate(String phone) {
        if (phone == null || phone.isBlank()) {
            throw UserAccountValidationException.invalidPhoneNumber("Phone number cannot be empty");
        }
        if (!VALIDATOR.matcher(phone).matches()) {
            throw UserAccountValidationException.invalidPhoneNumber("Invalid phone number format. Use E.164 (e.g., +989123456789)");
        }
    }

    @Override
    public List<Object> getAtomicValues() {
        return List.of(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
