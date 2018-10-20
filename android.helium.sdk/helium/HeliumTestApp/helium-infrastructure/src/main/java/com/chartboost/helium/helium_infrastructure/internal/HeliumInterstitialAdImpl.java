package com.chartboost.helium.helium_infrastructure.internal;

import android.util.Log;

import com.chartboost.helium.helium_common.event.Event;
import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_domain.PlacementId;
import com.chartboost.helium.helium_domain.ad.InterstitialLoadRequested;
import com.chartboost.helium.helium_domain.ad.InterstitialShowRequested;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAd;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAdDelegate;
import com.chartboost.helium.helium_infrastructure.NoAdFoundxception;
import com.chartboost.helium.helium_infrastructure.UIViewController;
import com.chartboost.helium.helium_interactors.RepoFactory;

public class HeliumInterstitialAdImpl implements HeliumInterstitialAd {
    private String TAG = "HeliumInterstitialAdImpl";
    private EventBus eventBus_;
    private HeliumAdContext context_;
    private RepoFactory repoFactory_;
    private PlacementId placementIdentifier_;

    private void loadAd() {
        context_.yourAreLoading();
        Event appEvent = InterstitialLoadRequested.of(placementIdentifier_);
        eventBus_.publish(appEvent);
    }
    private void showAd() {
        context_.yourAreShowing();
        Event appEvent = InterstitialShowRequested.of(placementIdentifier_);
        eventBus_.publish(appEvent);
    }

    @Override
    public String placementId() {
        if (placementIdentifier_ != null) {
            return placementIdentifier_.placementId();
        }
        return null;
    }

    @Override
    public void loadAdWithDelegate(HeliumInterstitialAdDelegate delegate) {
        if (context_ != null) {
            Log.d(TAG, "previous load ad request is still pending???");
        }
        HeliumInterstitialAdDelegate adDelegate = null;
        Object callee = this;
        context_ = HeliumAdContext.of(adDelegate, callee);
        loadAd();


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
