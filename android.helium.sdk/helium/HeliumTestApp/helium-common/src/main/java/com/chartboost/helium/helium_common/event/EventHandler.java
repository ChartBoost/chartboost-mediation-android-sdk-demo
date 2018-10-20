package com.chartboost.helium.helium_common.event;

public interface EventHandler<EventT> {
    void handle(EventT event);
}
