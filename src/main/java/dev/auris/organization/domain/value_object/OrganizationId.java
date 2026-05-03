package dev.auris.organization.domain.value_object;

import dev.auris.user_account.domain.exception.UserAccountValidationException;
import dev.auris.shared.building_block.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class OrganizationId extends ValueObject {
    private final UUID value;

    public OrganizationId(UUID value) {
        this.value = Objects.requireNonNull(value, "Organization Id value cannot be null");
    }

    public static OrganizationId generate() {
        return new OrganizationId(UUID.randomUUID());
    }

    public static OrganizationId fromString(String uuidString) {
        try {
            return new OrganizationId(UUID.fromString(uuidString));
        } catch (IllegalArgumentException e) {
            throw UserAccountValidationException.invalidIdentifier("OrganizationId", "format is not correct");
        }
    }


    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrganizationId that)) return false;
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
