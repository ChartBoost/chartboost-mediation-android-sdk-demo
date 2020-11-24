package com.chartboost.helium.helium_common.event.sourcing;

import com.chartboost.helium.helium_common.event.Event;

public interface EventStore {
    void store(Event event);
}
