package com.chartboost.helium.helium_infrastructure;

public interface HeliumRewardedVideoAd {
    String placementId();
    void loadAdWithDelegate(HeliumRewardedVideoAdDelegate delegate);
    void showAdWithViewController(UIViewController vc, HeliumRewardedVideoAdDelegate delegate);
}
