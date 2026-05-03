package dev.auris.organization.domain.value_object;

import dev.auris.shared.building_block.ValueObject;
import org.springframework.util.Assert;

import java.util.List;
import java.util.regex.Pattern;

public final class OfficePhoneNumber extends ValueObject {

    private static final Pattern IRAN_OFFICE_PHONE_PATTERN =
            Pattern.compile("^0[1-9]{2}\\d{5,8}$");

    private final String number;

    private OfficePhoneNumber(String number) {
        validate(number);
        this.number = number.replaceAll("\\s+", "");
    }

    public static OfficePhoneNumber of(String number) {
        return new OfficePhoneNumber(number);
    }

    private void validate(String number) {
        Assert.hasText(number, "Office phone number cannot be empty.");
        if (!IRAN_OFFICE_PHONE_PATTERN.matcher(number).matches()) {
            throw new IllegalArgumentException("Invalid Iranian office phone number format.");
        }
    }

    public String getNumber() {
        return number;
    }

    @Override
    public List<Object> getAtomicValues() {
        return List.of(number);
    }
}
