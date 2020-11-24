package com.chartboost.helium.helium_common.event;

import com.chartboost.helium.helium_common.value.time.DKDateTimeValue;
import com.chartboost.helium.helium_common.version.QVersion;

public interface Event {
    Object payload();
    EventType eventType();
    QVersion version();
    DKDateTimeValue occuredOn();
    String name();
}
