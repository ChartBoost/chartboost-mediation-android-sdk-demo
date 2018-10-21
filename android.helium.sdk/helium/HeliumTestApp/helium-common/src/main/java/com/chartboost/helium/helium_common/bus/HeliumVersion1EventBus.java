package com.chartboost.helium.helium_common.bus;


import com.chartboost.helium.helium_common.event.Event;
import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_common.event.EventHandler;
import com.chartboost.helium.helium_common.event.EventType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class HeliumVersion1EventBus implements EventBus {

    private CopyOnWriteArraySet<EventHandler<Event>> alreadyExistsSubscribers_;
    private ConcurrentHashMap<Integer, CopyOnWriteArrayList<EventHandler<Event>>> subscribers_;

    private HeliumVersion1EventBus() {
        subscribers_ = new ConcurrentHashMap<>();
        alreadyExistsSubscribers_ = new CopyOnWriteArraySet<>();
    }

    public static EventBus of() {
        return new HeliumVersion1EventBus();
    }

    @Override
    public void publish(Event event) {
        Integer subscriptionKey = Integer.valueOf(event.eventType().ordinal());
        if (subscribers_.get(subscriptionKey) != null) {
            CopyOnWriteArrayList<EventHandler<Event>> subscribers = subscribers_.get(subscriptionKey);
            for (EventHandler<Event> subscriber : subscribers) {
                if (subscriber != null) {
                    subscriber.handle(event);
                }
            }
        }
    }

    @Override
    public void subscribe(EventType eventType, EventHandler<Event> handler) {
        Integer subscriptionKey = Integer.valueOf(eventType.ordinal());

        //dont add duplicate handlers
        if (!alreadyExistsSubscribers_.contains(handler)) {
            CopyOnWriteArrayList<EventHandler<Event>> subscribers = null;
            //does subscriptionKey exists?
            if (subscribers_.get(subscriptionKey) != null) { //exists
                subscribers = subscribers_.get(subscriptionKey);
                subscribers.add(handler);
            } else { //does not exists
                subscribers = new CopyOnWriteArrayList<>();
                subscribers.add(handler);
                subscribers_.put(subscriptionKey, subscribers);
                alreadyExistsSubscribers_.add(handler);
            }
        }

    }

    @Override
    public void unSubscribe(EventType eventType, EventHandler<Event> handler) {
        Integer subscriptionKey = Integer.valueOf(eventType.ordinal());
        if (alreadyExistsSubscribers_.contains(handler)) {
            if (subscribers_.get(subscriptionKey) != null) { //exists
                CopyOnWriteArrayList<EventHandler<Event>> handlers = subscribers_.get(subscriptionKey);
                handlers.remove(handler);
                alreadyExistsSubscribers_.remove(handler);
            } else {
                //do nothing but record this incident
            }
        }
    }

}
