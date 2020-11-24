package com.chartboost.helium.helium_common.rtb;

public class RtbRegs {
    private RtbRegsExt ext;
    public RtbRegs() {}
    public void populate(RtbService rtbService) {
        ext = new RtbRegsExt();
        ext.populate(rtbService);
    }
}
