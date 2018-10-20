package com.chartboost.helium.helium_infrastructure.internal;

import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_domain.PlacementId;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAd;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAdDelegate;
import com.chartboost.helium.helium_infrastructure.NoAdFoundxception;
import com.chartboost.helium.helium_infrastructure.UIViewController;
import com.chartboost.helium.helium_interactors.RepoFactory;

public class HeliumInterstitialAdImpl implements HeliumInterstitialAd {
    private EventBus eventBus_;
    private HeliumAdContext context_;
    private RepoFactory repoFactory_;
    private PlacementId placementIdentifier_;

    @Override
    public String placementId() {
        if (placementIdentifier_ != null) {
            return placementIdentifier_.placementId();
        }
        return null;
    }

    @Override
    public void loadAdWithDelegate(HeliumInterstitialAdDelegate delegate) {
delegate.interstitialAdDidLoad(null, null);//todo
    }

    @Override
    public void showAdWithViewController(UIViewController vc, HeliumInterstitialAdDelegate delegate) throws NoAdFoundxception {

    }

    public void setPlacementId(String placementId) {
        placementIdentifier_ = PlacementId.of(placementId);
    }

    public void setEventBus(EventBus eventBus) {
        eventBus_ = eventBus;
    }

    public void setRepoFactory(RepoFactory repoFactory) {
        repoFactory_ = repoFactory;
    }

    public static class Builder {
        private String placementId_;
        private EventBus eventBus_;
        private RepoFactory repoFactory_;

        public Builder setPlacementId(String placementId) {
            placementId_ = placementId;
            return this;
        }

        public Builder setEventBus(EventBus eventBus) {
            eventBus_ = eventBus;
            return this;
        }

        public Builder setRepoFactory(RepoFactory repoFactory) {
            repoFactory_ = repoFactory;
            return this;
        }

        public HeliumInterstitialAd build() {
            HeliumInterstitialAdImpl interstitialAd = new HeliumInterstitialAdImpl();
            interstitialAd.setEventBus(eventBus_);
            interstitialAd.setPlacementId(placementId_);
            interstitialAd.setRepoFactory(repoFactory_);
            return interstitialAd;
        }

    }
}
