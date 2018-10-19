package com.chartboost.helium.helium_common.event;

import com.chartboost.helium.helium_common.value.string.StringValue;
import com.chartboost.helium.helium_common.value.time.DKDateTimeValue;

public class StoredEvent {
    private DKDateTimeValue occuredOn_;
    private StringValue body_;
    private StringValue javaClassName_;
    private StoredEventIdentifier identifier_;

    public <DomainEventType extends DomainEvent> DomainEventType asDomainEvent() {
        return null;
    }
}
