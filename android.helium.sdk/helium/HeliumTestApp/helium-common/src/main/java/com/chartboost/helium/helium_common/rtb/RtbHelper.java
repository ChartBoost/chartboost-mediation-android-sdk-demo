package com.chartboost.helium.helium_common.rtb;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class RtbHelper {
    private static Set<Object> cache_ = new HashSet<>();

    public static String createBidRequest() {
        BidRequest br = new BidRequest();
        br.populate(new RtbService());
        Gson gson = new Gson();
        String json = gson.toJson(br);

        return json;
    }
}
