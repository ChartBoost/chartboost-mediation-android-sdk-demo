package com.chartboost.helium.helium_common.event;

import com.chartboost.helium.helium_common.value.lawng.LongValue;
import com.chartboost.helium.helium_common.value.time.DKDateTimeValue;
import com.chartboost.helium.helium_common.version.QVersion;

public interface Event<PayloadType> {
    Class payloadClass();
    DKDateTimeValue occuredOn();
    QVersion eventVersion();
    Object payload();
    EventType eventType();
    DomainEventType domainEventType();
    LongValue identifier();
}
