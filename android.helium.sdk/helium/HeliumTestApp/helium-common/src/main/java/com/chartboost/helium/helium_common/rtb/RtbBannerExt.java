package com.chartboost.helium.helium_common.rtb;

class RtbBannerExt {
    private String placementtype;
    public RtbBannerExt() {}
    public void populate(RtbService rtbService) {
        placementtype = "interstitial";
    }
}
