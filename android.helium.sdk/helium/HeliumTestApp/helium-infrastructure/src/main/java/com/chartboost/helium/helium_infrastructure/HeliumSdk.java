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
    
    public void start(HeliumConfig config, HeliumSdkInitializeListener listener) {

    }

    public HeliumInterstitialAd interstitialAdProviderForPlacementId(String placemendId) {
        HeliumInterstitialAd interstitialAd = new HeliumInterstitialAdImpl.Builder()
                    .setEventBus(eventBus_)
                    .setPlacementId(placemendId)
                    .setRepoFactory(repoFactory_)
                    .build();
        return interstitialAd;
    }

    public HeliumRewardedVideoAd rewardedVideoAdProviderForPlacementId(String placemendId) {
        HeliumRewardedVideoAd rewardedVideoAd = null;
        return rewardedVideoAd;
    }
}
