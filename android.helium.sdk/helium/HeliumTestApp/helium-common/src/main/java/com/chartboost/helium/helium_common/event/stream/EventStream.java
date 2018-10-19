package com.chartboost.helium.helium_common.event.stream;

import com.chartboost.helium.helium_common.event.DomainEvent;

import java.util.List;

public interface EventStream {
    List<DomainEvent> allEvents();
}
