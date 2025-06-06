package com.example.chartboost.mediation.sdk.demo.java;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadResult;
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException;

public interface CustomFullscreenAdListener {
    void onAdLoaded(final String placementName, final ChartboostMediationFullscreenAdLoadResult adLoadResult);

    void onAdClosed(final String placementName, final @NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, final @Nullable ChartboostMediationAdException e, @Nullable Boolean hasNextAdQueued);

    void onAdShowFailure(final String placementName, @Nullable final Boolean hasAdQueued);

    void onAdShowSuccess(final String placementName, @Nullable Integer numberOfAdsReady);
}
