package dev.auris.organization.domain.exception;

public class InvitationCodeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Invitation code not found.";
    public InvitationCodeNotFoundException() {
        super(MESSAGE);
    }
}
