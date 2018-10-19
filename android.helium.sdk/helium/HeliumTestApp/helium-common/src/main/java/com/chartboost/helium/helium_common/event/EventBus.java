package com.chartboost.helium.helium_common.event;

public interface EventBus {
    void publish(Event event);
    void subscribe(EventType eventType, EventHandler<Event> handler);
    void unSubscribe(EventType eventType, EventHandler<Event> handler);
}
