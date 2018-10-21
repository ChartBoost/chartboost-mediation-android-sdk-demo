package com.chartboost.helium.helium_common.event;

public interface EventBus {
    void publish(Event event);
    void subscribe(EventType eventType, EventHandler handler);
    void unSubscribe(EventType eventType, EventHandler handler);
}
