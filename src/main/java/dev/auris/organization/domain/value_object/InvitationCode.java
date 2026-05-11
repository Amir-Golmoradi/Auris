package dev.auris.organization.domain.value_object;

import dev.auris.organization.domain.exception.InvalidInvitationCodeException;
import dev.auris.shared.building_block.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Pattern;

public final class InvitationCode extends ValueObject {
    private static final Logger log = LoggerFactory.getLogger(InvitationCode.class);

    private static final int EXPECTED_LENGTH = 8;
    private static final Pattern BASE62_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    private final String value;

    public InvitationCode(String value) {
        this.value = value;
    }

    public static InvitationCode of(String value){
        if (value == null || value.isBlank()){
            throw new InvalidInvitationCodeException("Invitation code must be exactly " + EXPECTED_LENGTH + " characters.");
        }
        if (value.length() != EXPECTED_LENGTH){
            throw new InvalidInvitationCodeException("Invitation code must be exactly " + EXPECTED_LENGTH + " characters.");
        }
        if (!BASE62_PATTERN.matcher(value).matches()){
            throw new InvalidInvitationCodeException("Invitation code contains invalid characters.");
        }
        return new InvitationCode(value);
    }

    @Override
    public String toString() {
        return "InvitationCode[REDACTED]"; // never expose in logs accidentally
    }
    @Override
    public List<Object> getAtomicValues() {
        return List.of(value);
    }
}
