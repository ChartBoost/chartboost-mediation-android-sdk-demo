package com.chartboost.helium.helium_interactors.store.cloud;
public class PrebidUrlBuilder {
    public static String buildAuctionsUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(ApiConstants.PREBID_BASE_URL);
        sb.append(ApiConstants.SEPERATOR);
        sb.append(ApiConstants.STAGE);
        sb.append(ApiConstants.SEPERATOR);
        sb.append(ApiConstants.RESOURCE);
        return sb.toString();
    }
}
