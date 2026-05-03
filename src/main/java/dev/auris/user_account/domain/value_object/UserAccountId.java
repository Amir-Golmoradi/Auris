package dev.auris.user_account.domain.value_object;

import dev.auris.user_account.domain.exception.UserAccountValidationException;
import dev.auris.shared.building_block.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public final class UserAccountId extends ValueObject {
    private final UUID value;

    public UserAccountId(UUID value) {
        this.value = Objects.requireNonNull(value, "UserAccountId value cannot be null");
    }

    public static UserAccountId generate() {
        return new UserAccountId(UUID.randomUUID());
    }

    public static UserAccountId fromString(String uuidString) {
        try {
            return new UserAccountId(UUID.fromString(uuidString));
        } catch (IllegalArgumentException e) {
            throw UserAccountValidationException.invalidIdentifier("UserAccountId", "format is not correct");
        }
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserAccountId that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public List<Object> getAtomicValues() {
        return List.of(value);
    }
}
