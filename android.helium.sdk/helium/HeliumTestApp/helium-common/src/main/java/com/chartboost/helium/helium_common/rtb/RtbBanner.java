package com.chartboost.helium.helium_common.rtb;

class RtbBanner {
    private int w;
    private int h;
    private int pos;
    private int topframe;
    private RtbBannerExt ext;
    public RtbBanner() {}
    public void populate(RtbService rtbService) {
        w = 1440;
        h = 2560;
        pos = 7;
        topframe = 1;
        ext = new RtbBannerExt();
        ext.populate(rtbService);
    }
}
