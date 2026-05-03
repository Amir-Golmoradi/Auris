package dev.auris.shared.building_block;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all Aggregate Roots (the most important DDD Building Block).
 * <p>
 * Combines Entity + IAggregateRoot.
 * Manages a list of Domain Events that are raised during Organization operations.
 * </p>
 * <p>
 * Usage example in Post Aggregate:
 * <pre>
 * protected void publish() {
 *     if (isPublished) throw new DomainException("Already published");
 *     // Organization logic
 *     addDomainEvent(new PostPublishedEvent(getId(), getTitle()));
 * }
 * </pre>
 * </p>
 * <p>
 * This is the exact implementation of "Model-Driven Design" and "Letting the Bones Show"
 * from Chapter 3 of Eric Evans' DDD book.
 * </p>
 *
 * @param <TId>        type of the Aggregate identity (e.g. PostId)
 * @param <EventType> type of Domain Events this Aggregate can raise (type-safe!)
 * @since 1.0
 */
public abstract class AggregateRoot<TId, EventType extends DomainEvent>
        extends BaseEntity<TId>
        implements IAggregateRoot {

    private final List<EventType> domainEvents = new ArrayList<>();

    protected AggregateRoot(TId TId) {
        super(TId);
    }

    @Override
    public List<EventType> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    @Override
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    /**
     * Protected method to be called from inside the Aggregate when a Organization event happens.
     * Keeps events inside the Aggregate boundary.
     */
    protected void addDomainEvent(EventType event) {
        domainEvents.add(event);
    }
}