package com.chartboost.helium.helium_common.event;

public interface EventHandler<EventT extends Event> {
    void handle(EventT event);
}
