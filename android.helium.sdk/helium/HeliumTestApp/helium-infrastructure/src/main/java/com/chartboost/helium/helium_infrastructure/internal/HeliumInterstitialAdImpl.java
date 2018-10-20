package com.chartboost.helium.helium_infrastructure.internal;

import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAd;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAdDelegate;
import com.chartboost.helium.helium_infrastructure.NoAdFoundxception;
import com.chartboost.helium.helium_infrastructure.UIViewController;
import com.chartboost.helium.helium_interactors.RepoFactory;

public class HeliumInterstitialAdImpl implements HeliumInterstitialAd {
    @Override
    public String placementId() {
        return null;
    }

    @Override
    public void loadAdWithDelegate(HeliumInterstitialAdDelegate delegate) {

    }

    @Override
    public void showAdWithViewController(UIViewController vc, HeliumInterstitialAdDelegate delegate) throws NoAdFoundxception {

    }

    public static class Builder {
        public Builder setPlacementId(String placementId) {
            return this;
        }

        public Builder setEventBus(EventBus eventBus) {
            return this;
        }

        public Builder setRepoFactory(RepoFactory repoFactory) {
            return this;
        }

        public HeliumInterstitialAd build() {
            HeliumInterstitialAdImpl interstitialAd = new HeliumInterstitialAdImpl();
            return interstitialAd;
        }

    }
}
