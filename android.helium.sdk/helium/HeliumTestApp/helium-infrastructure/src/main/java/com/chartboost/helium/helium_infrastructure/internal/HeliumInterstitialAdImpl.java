package com.chartboost.helium.helium_infrastructure.internal;

import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAd;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAdDelegate;
import com.chartboost.helium.helium_infrastructure.NoAdFoundxception;
import com.chartboost.helium.helium_infrastructure.UIViewController;

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
}
