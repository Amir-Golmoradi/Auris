package dev.auris.user_account.domain.value_object;

import dev.auris.user_account.domain.exception.UserAccountValidationException;
import dev.auris.shared.building_block.ValueObject;

import java.util.List;
import java.util.regex.Pattern;

public final class UserName extends ValueObject {
    private static final Pattern USERNAME_VALIDATOR = Pattern.compile("^[A-Z][A-Za-z0-9]*(?: [A-Za-z0-9]+)*$");
    private final String value;

    private UserName(String value) {
        validateUserName(value);
        this.value = value;
    }

    public static UserName of(String username) {
        return new UserName(username);
    }

    public String getValue() {
        return value;
    }

    private void validateUserName(String username) {
        if (!USERNAME_VALIDATOR.matcher(username).matches()) {
            throw UserAccountValidationException.invalidUsername("UserName is not valid");
        }
    }

    @Override
    public List<Object> getAtomicValues() {
        return List.of(value);
    }
}
