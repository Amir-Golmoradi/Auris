package dev.auris.organization.domain.exception;

public class InvalidInvitationCodeException extends RuntimeException {
    public InvalidInvitationCodeException() {
        super("Invitation code is invalid");
    }
}
