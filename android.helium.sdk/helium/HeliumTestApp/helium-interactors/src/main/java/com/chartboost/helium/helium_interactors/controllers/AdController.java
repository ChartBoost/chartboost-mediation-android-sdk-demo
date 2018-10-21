package com.chartboost.helium.helium_interactors.controllers;

import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_interactors.event_handlers.AdFound_EventHandler;
import com.chartboost.helium.helium_interactors.event_handlers.InterstitialLoadRequested_EventHandler;
import com.chartboost.helium.helium_interactors.event_handlers.InterstitialShowRequested_EventHandler;
import com.chartboost.helium.helium_domain.service.SdkRoutingDomainService;
import com.chartboost.helium.helium_interactors.RepoFactory;

public class AdController {
    private RepoFactory repoFactory_;
    private EventBus eventBus_;
    private SdkRoutingDomainService sdkRoutingDomainService_;

    private InterstitialLoadRequested_EventHandler interstitialAdRequested_EventHandler_;
    private InterstitialShowRequested_EventHandler interstitialShowAdRequested_EventHandler_;
    private AdFound_EventHandler adFound_EventHandler_;

    private void init() {
        interstitialAdRequested_EventHandler_ = InterstitialLoadRequested_EventHandler.of(eventBus_, repoFactory_);
        interstitialShowAdRequested_EventHandler_ = InterstitialShowRequested_EventHandler.of(eventBus_, repoFactory_);
        adFound_EventHandler_ = AdFound_EventHandler.of(eventBus_, repoFactory_, sdkRoutingDomainService_);
    }

    private AdController(EventBus eventBus, RepoFactory repoFactory, SdkRoutingDomainService sdkRoutingDomainService) {
        repoFactory_ = repoFactory;
        eventBus_ = eventBus;
        sdkRoutingDomainService_ = sdkRoutingDomainService;
        init();
    }

    public static AdController of(EventBus eventBus, RepoFactory repoFactory, SdkRoutingDomainService sdkRoutingDomainService) {
        return new AdController(eventBus, repoFactory, sdkRoutingDomainService);
    }

    public static final class Builder {
        private RepoFactory repoFactory_;
        private EventBus eventBus_;
        private SdkRoutingDomainService sdkRoutingDomainService_;

        private InterstitialLoadRequested_EventHandler interstitialAdRequested_EventHandler_;
        private InterstitialShowRequested_EventHandler interstitialShowAdRequested_EventHandler_;
        private AdFound_EventHandler adFound_EventHandler_;

        private Builder() {}

        public static Builder of() {
            return new Builder();
        }

        public Builder setRepoFactory(RepoFactory repoFactory) {
            repoFactory_ = repoFactory;
            return this;
        }

        public Builder setEventBus(EventBus eventBus) {
            eventBus_ = eventBus;
            return this;
        }

        public Builder setSdkRoutingDomainService(SdkRoutingDomainService sdkRoutingDomainService) {
            sdkRoutingDomainService_ = sdkRoutingDomainService;
            return this;
        }

        public Builder setInterstitialAdRequested_EventHandler(InterstitialLoadRequested_EventHandler interstitialAdRequested_EventHandler) {
            interstitialAdRequested_EventHandler_ = interstitialAdRequested_EventHandler;
            return this;
        }

        public Builder setInterstitialShowAdRequested_EventHandler(InterstitialShowRequested_EventHandler interstitialShowAdRequested_EventHandler) {
            interstitialShowAdRequested_EventHandler_ = interstitialShowAdRequested_EventHandler;
            return this;
        }

        public Builder setAdFound_EventHandler(AdFound_EventHandler adFound_EventHandler) {
            adFound_EventHandler_ = adFound_EventHandler;
            return this;
        }

        public AdController build() {
            AdController adController = AdController.of(eventBus_, repoFactory_, sdkRoutingDomainService_);
            return adController;
        }

    }
}
