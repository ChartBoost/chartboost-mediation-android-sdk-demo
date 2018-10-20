package com.chartboost.helium.helium_domain.ad;

import com.chartboost.helium.helium_common.event.EventHandler;
import com.chartboost.helium.helium_common.event.EventType;

public class InterstitialLoadRequested_EventHandler implements EventHandler<InterstitialLoadRequested> {
    @Override
    public void handle(InterstitialLoadRequested event) {
        if (event.eventType() == EventType.App) {
            doHandle(event);
        }
    }

    private void doHandle(InterstitialLoadRequested event) {
        
    }
}
