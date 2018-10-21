package com.chartboost.helium.helium_interactors.event_handlers;

import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_common.event.EventHandler;
import com.chartboost.helium.helium_common.event.EventType;
import com.chartboost.helium.helium_domain.ad.InterstitialLoadRequested;
import com.chartboost.helium.helium_interactors.RepoFactory;

public class InterstitialLoadRequested_EventHandler implements EventHandler<InterstitialLoadRequested> {
    public static InterstitialLoadRequested_EventHandler of(EventBus eventBus, RepoFactory repoFactory) {
        return null;
    }

    @Override
    public void handle(InterstitialLoadRequested event) {
        if (event.eventType() == EventType.App) {
            doHandle(event);
        }
    }

    private void doHandle(InterstitialLoadRequested event) {

    }
}
