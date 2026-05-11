package dev.auris.identity.infrastructure.exception;

public class InvalidRegisteredClientException extends RuntimeException {
    //"شناسه کلاینت معتبر نیست. لطفا دوباره بررسی کنید."
    private static final String MESSAGE = "Invalid client id, please try again";

    public InvalidRegisteredClientException() {
        super(MESSAGE);
    }

    public InvalidRegisteredClientException(String message) {
    }
}
