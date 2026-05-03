package dev.auris.organization.domain.value_object;

import dev.auris.shared.building_block.ValueObject;
import org.springframework.util.Assert;

import java.util.List;

public final class OrganizationName extends ValueObject {
    private final String value;

    private OrganizationName(String value) {
        validate(value);
        this.value = value.trim();
    }

    public static OrganizationName of(String value) {
        return new OrganizationName(value);
    }

    private void validate(String value) {
        Assert.hasText(value, "Organization Name cannot be empty.");

        String trimmed = value.trim();
        if (trimmed.length() < 3 || trimmed.length() > 100) {
            throw new IllegalArgumentException("Organization Name must be between 3 and 100 characters.");
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
