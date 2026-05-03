package dev.auris.shared.building_block;

import java.util.List;

/**
 * Marker interface for Aggregate Roots.
 * <p>
 * An Aggregate Root is the only entity that external objects are allowed to hold references to.
 * It is responsible for maintaining consistency within its Aggregate boundary.
 * </p>
 * <p>
 * This non-generic interface allows polymorphism (List&lt;IAggregateRoot&gt;) in repositories,
 * unit of work, and event publishers — even though each Aggregate has its own event type.
 * </p>
 * <p>
 * Critical for Clean Architecture and CQRS.
 * </p>
 *
 * @see AggregateRoot
 * @since 1.0
 */
public interface IAggregateRoot {

    /**
     * Returns all domain events that occurred inside this Aggregate.
     * The returned list is immutable.
     *
     * @return immutable list of domain events
     */
    List<? extends DomainEvent> getDomainEvents();

    /**
     * Clears all domain events after they have been published/saved.
     * Called by the infrastructure layer after successful persistence.
     */
    void clearDomainEvents();
}