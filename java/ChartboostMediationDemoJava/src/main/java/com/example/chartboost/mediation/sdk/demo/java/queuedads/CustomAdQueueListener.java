package com.example.chartboost.mediation.sdk.demo.java.queuedads;

public interface CustomAdQueueListener {
    void onFullScreenAdQueueUpdated(final String placementName, final int numberOfAdsReady);
}
