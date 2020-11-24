package com.chartboost.helium.helium_infrastructure;

public interface HeliumInterstitialAd {
    String placementId();
    void loadAd();
    void showAdWithViewController(UIViewController vc) throws NoAdFoundxception;
}
