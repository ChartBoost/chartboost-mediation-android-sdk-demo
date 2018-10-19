package com.chartboost.helium.helium_common.rtb;

public class RtbImpression {
    private String displaymanager;
    private String displaymanagerver;
    private int instl;
    private String tagid;
    private int secure;
    private RtbVideo video;
    private RtbBanner banner;
    public RtbImpression() {}
    public RtbImpression populate(RtbService rtbService) {
        displaymanager = new String("Chartboost-Android-SDK");
        displaymanagerver = new String("7.3.0");
        instl = 1;
        tagid = new String("CBLocation");
        secure = 1;
        video = new RtbVideo();
        video.populate(rtbService);
        banner = new RtbBanner();
        banner.populate(rtbService);
        return this;
    }
}
