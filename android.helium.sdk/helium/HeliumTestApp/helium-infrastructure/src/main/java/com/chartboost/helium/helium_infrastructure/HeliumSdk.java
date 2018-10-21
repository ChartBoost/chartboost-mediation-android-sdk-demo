package com.chartboost.helium.helium_infrastructure;

import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_infrastructure.internal.HeliumInterstitialAdImpl;
import com.chartboost.helium.helium_interactors.RepoFactory;
import com.chartboost.helium.helium_interactors.controllers.AdController;
import com.chartboost.helium.helium_interactors.controllers.BidController;
import com.chartboost.helium.helium_interactors.controllers.ConfigController;
import com.chartboost.helium.helium_interactors.controllers.PartnerController;
import com.chartboost.helium.helium_interactors.repos.HeliumRepo;

import java.util.logging.Logger;

public class HeliumSdk {
    private EventBus eventBus_;
    private HeliumSdkInitializeListener delegate_;
    private Logger defaultHeliumLogger_;
    private HeliumConfig config_;
    private AdController adController_;
    private BidController bidController_;
    private PartnerController partnerController_;
    private ConfigController configController_;
    private RepoFactory repoFactory_;
    private HeliumRepo heliumRepo_;

    public HeliumSdk setEventBus(EventBus eventBus) {
        this.eventBus_ = eventBus;
        return this;
    }

    public HeliumSdk setDelegate(HeliumSdkInitializeListener delegate) {
        this.delegate_ = delegate;
        return this;
    }

    public HeliumSdk setDefaultHeliumLogger(Logger defaultHeliumLogger) {
        this.defaultHeliumLogger_ = defaultHeliumLogger;
        return this;
    }

    public HeliumSdk setConfig(HeliumConfig config) {
        this.config_ = config;
        return this;
    }

    public HeliumSdk setAdController(AdController adController) {
        this.adController_ = adController;
        return this;
    }

    public HeliumSdk setBidController(BidController bidController) {
        this.bidController_ = bidController;
        return this;
    }

    public HeliumSdk setPartnerController(PartnerController partnerController) {
        this.partnerController_ = partnerController;
        return this;
    }

    public HeliumSdk setConfigController(ConfigController configController) {
        this.configController_ = configController;
        return this;
    }

    public HeliumSdk setRepoFactory(RepoFactory repoFactory) {
        this.repoFactory_ = repoFactory;
        return this;
    }

    public HeliumSdk setHeliumRepo(HeliumRepo heliumRepo) {
        this.heliumRepo_ = heliumRepo;
        return this;
    }

    public void start(HeliumConfig config, HeliumSdkInitializeListener listener) {

    }
//
//    public HeliumInterstitialAd interstitialAdProviderForPlacementId(String placemendId) {
//        HeliumInterstitialAd interstitialAd = new HeliumInterstitialAdImpl.Builder()
//                    .setEventBus(eventBus)
//                    .setPlacementId(placemendId)
//                    .setRepoFactory(repoFactory)
//                    .build();
//        return interstitialAd;
//    }

    public HeliumRewardedVideoAd rewardedVideoAdProviderForPlacementId(String placemendId) {
        HeliumRewardedVideoAd rewardedVideoAd = null;
        return rewardedVideoAd;
    }

    public static class Builder {

        private EventBus eventBus_;
        private HeliumSdkInitializeListener delegate_;
        private Logger defaultHeliumLogger_;
        private HeliumConfig config_;
        private AdController adController_;
        private BidController bidController_;
        private PartnerController partnerController_;
        private ConfigController configController_;
        private RepoFactory repoFactory_;
        private HeliumRepo heliumRepo_;
        
        public Builder setEventBus(EventBus eventBus) {
            this.eventBus_ = eventBus;
            return this;
        }

        public Builder setDelegate(HeliumSdkInitializeListener delegate) {
            this.delegate_ = delegate;
            return this;
        }

        public Builder setDefaultHeliumLogger(Logger defaultHeliumLogger) {
            this.defaultHeliumLogger_ = defaultHeliumLogger;
            return this;
        }

        public Builder setConfig(HeliumConfig config) {
            this.config_ = config;
            return this;
        }

        public Builder setAdController(AdController adController) {
            this.adController_ = adController;
            return this;
        }

        public Builder setBidController(BidController bidController) {
            this.bidController_ = bidController;
            return this;
        }

        public Builder setPartnerController(PartnerController partnerController) {
            this.partnerController_ = partnerController;
            return this;
        }

        public Builder setConfigController(ConfigController configController) {
            this.configController_ = configController;
            return this;
        }

        public Builder setRepoFactory(RepoFactory repoFactory) {
            this.repoFactory_ = repoFactory;
            return this;
        }

        public Builder setHeliumRepo(HeliumRepo heliumRepo) {
            this.heliumRepo_ = heliumRepo;
            return this;
        }

        public HeliumSdk build() {
            HeliumSdk sdk = new HeliumSdk();
            sdk.setAdController(adController_);
            return sdk;
        }

    }
}
