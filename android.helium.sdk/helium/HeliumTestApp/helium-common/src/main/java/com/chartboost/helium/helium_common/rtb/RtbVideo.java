package com.chartboost.helium.helium_common.rtb;

import java.util.ArrayList;
import java.util.List;

class RtbVideo {
    private List<String> mimes;
    private int w;
    private int h;
    private int placement;
    private int pos;
    private List<Integer> companiontype;
    private RtbVideoExt ext;
    public RtbVideo() {}
    public void populate(RtbService rtbService) {
        mimes = new ArrayList<>();
        mimes.add("video/mp4");
        w = 1440;
        h = 2560;
        placement = 5;
        pos = 7;
        companiontype = new ArrayList<>();
        companiontype.add(1);
        companiontype.add(3);
        ext = new RtbVideoExt();
        ext.populate(rtbService);
    }
}
