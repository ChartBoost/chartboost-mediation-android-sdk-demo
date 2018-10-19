package com.chartboost.helium.helium_infrastructure;

public interface HeliumInterstitialAdDelegate {
    void interstitialAdDidLoad(HeliumInterstitialAd heliumInterstitialAd, NoAdFoundxception error);
    void interstitialAdDidShow(HeliumInterstitialAd heliumInterstitialAd, NoAdFoundxception error);
}
