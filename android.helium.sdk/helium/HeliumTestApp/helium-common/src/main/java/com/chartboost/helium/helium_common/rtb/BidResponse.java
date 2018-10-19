package com.chartboost.helium.helium_common.rtb;

import com.chartboost.helium.helium_common.common.SimpleBuffer;
import com.chartboost.helium.helium_domain.bid.BidAggregate;
import com.google.gson.Gson;

public class BidResponse {
    private RtbResponse rtbResponse_;
    private SimpleBuffer adContents_;

    public BidResponse(SimpleBuffer adContents) {
        adContents_ = adContents;
        String strAdContents = adContents.asString();
        if (strAdContents != null) {
            rtbResponse_ = parse(strAdContents);
        }
    }

    private RtbResponse parse(String strAdContents) {
        Gson gson = new Gson();
        RtbResponse rtbResponse = gson.fromJson(strAdContents, RtbResponse.class);
        return rtbResponse;
    }

    public BidAggregate extractBid(SimpleBuffer adContents) {
        BidAggregate bidAggregate = null; // = BidAggregate.with(adContents_);
        return bidAggregate;
    }

//    public Sdk extractSdk() {
//        String partnerSdkName = new String("chartboost");
//        String partnerSdkVersion = new String("7.2.1");
//        Sdk sdk = Sdk.from(partnerSdkName, partnerSdkVersion);
//        return sdk;
//    }
//
//    public Route extractRoute(Sdk sdk) {
//        Route route = Route.from(sdk);
//        return route;
//    }

    public static BidResponse from(SimpleBuffer adContents) {
        return new BidResponse(adContents);
    }
}
