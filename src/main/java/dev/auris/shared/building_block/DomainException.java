package dev.auris.shared.building_block;

/**
 * Base class for all domain-specific Organization rule violations.
 * <p>
 * Use this when the domain model wants to enforce an invariant or Organization rule
 * (e.g., "Cannot publish a post that is already published").
 * These exceptions are thrown directly from inside Entities/Aggregates.
 * </p>
 * <p>
 * Extending RuntimeException makes them unchecked so they can bubble up to the
 * Application layer for proper translation to user-friendly responses.
 * This keeps the domain model clean and focused only on Organization rules.
 * </p>
 *
 * @see AggregateRoot
 * @since 1.0
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException() {
        super();
    }

    protected DomainException(String message) {
        super(message);
    }

    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}