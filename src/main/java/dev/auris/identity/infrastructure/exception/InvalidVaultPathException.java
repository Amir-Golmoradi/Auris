package dev.auris.identity.infrastructure.exception;

public class InvalidVaultPathException extends RuntimeException {
    public InvalidVaultPathException(String message) {
        super(message);
    }
}
