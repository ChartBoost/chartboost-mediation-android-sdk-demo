package com.chartboost.helium.helium_infrastructure;

public interface Helium {
    void initialize(HeliumSdkInitializeListener listener);
    HeliumInterstitialAd interstitalAd(String placementId, HeliumInterstitialAdDelegate delegate);
    HeliumRewardedVideoAd rewardedVideoAd(String placementId, HeliumRewardedVideoAdDelegate delegate);
}
