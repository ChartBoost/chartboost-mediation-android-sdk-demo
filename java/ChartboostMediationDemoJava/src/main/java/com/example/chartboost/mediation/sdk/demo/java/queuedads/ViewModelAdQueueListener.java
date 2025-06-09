package com.example.chartboost.mediation.sdk.demo.java.queuedads;

public interface ViewModelAdQueueListener {
    void onFullScreenAdQueueUpdated(final String placementName, final int numberOfAdsReady);
}
