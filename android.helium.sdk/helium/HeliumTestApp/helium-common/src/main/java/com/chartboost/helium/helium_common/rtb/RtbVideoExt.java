package com.chartboost.helium.helium_common.rtb;

class RtbVideoExt {
    private String placementtype;
    public RtbVideoExt() {}
    public void populate(RtbService rtbService) {
        placementtype = new String("interstitial");
    }
}
