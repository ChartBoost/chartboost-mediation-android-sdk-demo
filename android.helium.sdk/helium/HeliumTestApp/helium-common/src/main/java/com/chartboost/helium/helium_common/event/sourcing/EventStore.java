package com.chartboost.helium.helium_common.event.sourcing;

import com.chartboost.helium.helium_common.event.DomainEvent;
import com.chartboost.helium.helium_common.event.ReplayableDomainEvent;
import com.chartboost.helium.helium_common.event.stream.EventStream;
import com.chartboost.helium.helium_common.event.stream.EventStreamIdentifier;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementors of EventStore MUST make sure that they "open" the store
 * before allowing put operations.
 * Similarly, it is not the responsibility of callers to close the store.
 * EventStore must close itself whenever app is closed.
 */
public interface EventStore {

    void putInStream(EventStreamIdentifier streamId, List<DomainEvent> domainEvents);
    List<ReplayableDomainEvent> allDomainEventsSince(LocalDateTime startDateTime);
    EventStream eventStreamFor(EventStreamIdentifier streamId);

}
