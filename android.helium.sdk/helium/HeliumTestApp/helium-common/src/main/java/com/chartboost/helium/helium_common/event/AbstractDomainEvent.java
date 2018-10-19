package com.chartboost.helium.helium_common.event;

import com.chartboost.helium.helium_common.value.lawng.LongValue;
import com.chartboost.helium.helium_common.value.time.DKDateTimeValue;
import com.chartboost.helium.helium_common.version.QVersion;

abstract public class AbstractDomainEvent implements DomainEvent<DomainPayload> {
    private DomainPayload payload_;
    private EventType eventType_;
    private DomainEventType domainEventType_;
    private DKDateTimeValue occuredOn_;
    private Class class_;
    private QVersion eventVersion_;

    protected AbstractDomainEvent(DomainPayload payload, EventType eventType) {
        payload_ = payload;
        eventType_ = eventType;
        occuredOn_ = DKDateTimeValue.now();
        class_ = payload_.getClass();
        eventVersion_ = null; //TODO
    }

    @Override
    public Class payloadClass() {
        return class_;
    }

    @Override
    public DKDateTimeValue occuredOn() {
        return DKDateTimeValue.now();
    }

    @Override
    public QVersion eventVersion() {
        return eventVersion_;
    }

    @Override
    public Object payload() {
        return payload_;
    }

    @Override
    public EventType eventType() {
        return eventType_;
    }

    @Override
    public LongValue identifier() {
        return null;
    }
}
