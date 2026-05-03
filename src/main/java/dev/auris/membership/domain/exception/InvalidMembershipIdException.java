package dev.auris.membership.domain.exception;

public class InvalidMembershipIdException extends RuntimeException {
    public InvalidMembershipIdException(String message) {
        super(message);
    }
}
