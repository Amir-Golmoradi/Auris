package dev.auris.membership.domain.value_object;

import dev.auris.membership.domain.exception.InvalidMembershipIdException;
import dev.auris.shared.building_block.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class MembershipId extends ValueObject {

    private final UUID value;

    private MembershipId(UUID value) {
        Objects.requireNonNull(value, "MembershipId value must not be null");
        this.value = value;
    }

    public static MembershipId generate() {
        return new MembershipId(UUID.randomUUID());
    }

    public static MembershipId of(UUID value) {
        return new MembershipId(value);
    }

    public static MembershipId of(String value) {
        try {
            return new MembershipId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new InvalidMembershipIdException(value);
        }
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public List<Object> getAtomicValues() {
        return List.of(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MembershipId other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}