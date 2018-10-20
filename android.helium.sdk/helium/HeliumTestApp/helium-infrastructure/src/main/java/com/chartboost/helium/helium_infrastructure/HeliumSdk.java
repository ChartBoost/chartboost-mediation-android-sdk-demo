package com.chartboost.helium.helium_infrastructure;

import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_infrastructure.internal.HeliumInterstitialAdImpl;
import com.chartboost.helium.helium_interactors.RepoFactory;

import java.util.logging.Logger;

public class HeliumSdk {
    EventBus eventBus;
    HeliumSdkInitializeListener delegate;
    Logger defaultHeliumLogger;
    HeliumConfig config;
    AdController adController;
    BidController bidController;
    PartnerController partnerController;
    ConfigController configController;
    RepoFactory repoFactory;
    HeliumRepo heliumRepo;
    
    public void start(HeliumConfig config, HeliumSdkInitializeListener listener) {

    }

    public HeliumInterstitialAd interstitialAdProviderForPlacementId(String placemendId) {
        
        HeliumInterstitialAdImpl.Builder builder = new HeliumInterstitialAdImpl.Builder();
        builder.setEventBus()
        HeliumInterstitialAd interstitialAd = builder.build();
        return interstitialAd;
    }

    public HeliumRewardedVideoAd rewardedVideoAdProviderForPlacementId(String placemendId) {
        HeliumRewardedVideoAd rewardedVideoAd = null;
        return rewardedVideoAd;
    }
}
