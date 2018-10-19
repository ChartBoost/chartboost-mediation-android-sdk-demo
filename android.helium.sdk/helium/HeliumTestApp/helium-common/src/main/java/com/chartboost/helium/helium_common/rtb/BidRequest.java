package com.chartboost.helium.helium_common.rtb;

import java.util.ArrayList;
import java.util.List;

public class BidRequest {
    private List<RtbImpression> imp;
    private RtbApp app;
    private RtbDevice device;
    private RtbRegs regs;
    private int test;
    public BidRequest() {}

    public BidRequest populate(RtbService rtbService) {
        //imp
        imp = new ArrayList<>();
        RtbImpression impression = new RtbImpression();
        impression.populate(rtbService);
        imp.add(impression);

        //app
        app = new RtbApp();
        app.populate(rtbService);

        //device
        device = new RtbDevice();
        device.populate(rtbService);

        //regs
        regs = new RtbRegs();
        regs.populate(rtbService);

        //test
        test = 0;

        return this;
    }
}
