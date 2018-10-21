package com.chartboost.helium.helium_interactors.event_handlers;

import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_common.event.EventHandler;
import com.chartboost.helium.helium_common.event.EventType;
import com.chartboost.helium.helium_domain.ad.InterstitialLoadRequested;
import com.chartboost.helium.helium_interactors.RepoFactory;

public class InterstitialLoadRequested_EventHandler implements EventHandler<InterstitialLoadRequested> {
    private String TAG = "InterstitialLoadRequested_EventHandler";
    private final EventBus eventBus_;
    private final RepoFactory repoFactory_;

    private InterstitialLoadRequested_EventHandler(final EventBus eventBus, final RepoFactory repoFactory) {
        eventBus_ = eventBus;
        repoFactory_ = repoFactory;

        eventBus_.subscribe(EventType.App, this);
    }
    public static InterstitialLoadRequested_EventHandler of(EventBus eventBus, RepoFactory repoFactory) {
        return new InterstitialLoadRequested_EventHandler(eventBus, repoFactory);
    }

    @Override
    public void handle(InterstitialLoadRequested event) {
        if (event == null) {
            return;
        }
        if (event.eventType() == EventType.App) {
            doHandle(event);
        }
    }

    private void doHandle(InterstitialLoadRequested event) {

    }
}
