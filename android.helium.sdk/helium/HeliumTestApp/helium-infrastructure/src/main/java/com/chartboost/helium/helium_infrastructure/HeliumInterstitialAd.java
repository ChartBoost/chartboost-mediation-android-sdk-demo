package com.chartboost.helium.helium_infrastructure;

public interface HeliumInterstitialAd {
    String placementId();
    void loadAdWithDelegate(HeliumInterstitialAdDelegate delegate);
    void showAdWithViewController(UIViewController vc, HeliumInterstitialAdDelegate delegate) throws NoAdFoundxception;
}
