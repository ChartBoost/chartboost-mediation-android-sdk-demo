package com.chartboost.helium.helium_common.rtb;

public class RtbDevice {
    private String ua;
    private int lmt;
    private int devicetype;
    private String make;
    private String model;
    private String os;
    private String osv;
    private int w;
    private int h;
    private double pxratio;
    private String language;
    private String carrier;
    private int connectiontype;
    private String ifa;
    public RtbDevice() {}
    public void populate(RtbService rtbService) {
        ua = new String("Chartboost-Android-SDK  7.3.0");
        lmt = 1;
        devicetype = 4;
        make = new String("Huawei");
        model = new String("Nexus 6P");
        os = new String("Android");
        osv = new String("8.1.0");
        w = 1440;
        h = 2560;
        pxratio = 3.5;
        language = new String("en");
        carrier = new String("");
        connectiontype = 0;
        ifa = new String("b22e7d96-f27a-44fa-baa5-2afdf1577d70");

    }
}
