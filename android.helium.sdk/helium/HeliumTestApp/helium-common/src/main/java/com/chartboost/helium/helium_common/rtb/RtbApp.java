package com.chartboost.helium.helium_common.rtb;

public class RtbApp {
    private String id;
    private String bundle;
    public RtbApp() {}
    public void populate(RtbService rtbService) {
        id = new String("4f7b433509b6025804000002");
        bundle = new String("com.chartboost.sdk.sample.cbtest");
    }
}
