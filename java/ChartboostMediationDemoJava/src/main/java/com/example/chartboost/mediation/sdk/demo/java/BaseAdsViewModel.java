package com.example.chartboost.mediation.sdk.demo.java;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationAdShowResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadRequest;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdView;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdViewListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueue;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdShowListener;
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException;
import com.chartboost.chartboostmediationsdk.domain.Keywords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseAdsViewModel extends ViewModel {

    protected String interstitialPlacement = "";
    protected String rewardedPlacement = "";
    protected String bannerPlacement = "";

    private final List<String> backingUiLogs = new ArrayList<>();
    private final MutableLiveData<List<String>> uiLogs = new MutableLiveData<>(new ArrayList<>());

    public final LiveData<List<String>> getUiLogs() {
        return uiLogs;
    }

    private final ChartboostMediationBannerAdViewListener bannerAdListener;
    private final ChartboostMediationBannerAdLoadListener bannerAdLoadListener;

    public BaseAdsViewModel() {
        // Initialize banner listeners
        this.bannerAdListener = new ChartboostMediationBannerAdViewListener() {
            @Override
            public void onAdClicked(@NonNull String s) {
                addUiLogs("Banner clicked. Placement: " + s);
            }

            @Override
            public void onAdImpressionRecorded(@NonNull String s) {
                addUiLogs("Banner impression recorded. Placement: " + s);
            }

            @Override
            public void onAdViewAdded(@NonNull String s, @Nullable View view) {
                addUiLogs("Banner Ad view added. Placement: " + s);
            }
        };

        this.bannerAdLoadListener = new ChartboostMediationBannerAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull ChartboostMediationBannerAdLoadResult chartboostMediationBannerAdLoadResult) {
                if (chartboostMediationBannerAdLoadResult.getError() == null) {
                    addUiLogs("Banner loaded for placement: " + chartboostMediationBannerAdLoadResult.getPlacement());
                } else {
                    addUiLogs(String.format("Error loading banner ad for placement %s, code %s: %s",
                            chartboostMediationBannerAdLoadResult.getPlacement(),
                            chartboostMediationBannerAdLoadResult.getError().getCode(),
                            chartboostMediationBannerAdLoadResult.getError().getMessage()));
                }
            }
        };
    }

    public void setup(final String interstitialPlacement, final String rewardedPlacement, final String bannerPlacement) {
        this.interstitialPlacement = interstitialPlacement;
        this.rewardedPlacement = rewardedPlacement;
        this.bannerPlacement = bannerPlacement;
    }

    public ChartboostMediationBannerAdView createBanner(@NonNull final Context context) {
        return new ChartboostMediationBannerAdView(context, bannerPlacement, ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD, bannerAdListener);
    }

    public void loadBanner(@NonNull final ChartboostMediationBannerAdView banner) {
        banner.loadFromJava(
                new ChartboostMediationBannerAdLoadRequest(bannerPlacement, new Keywords(), ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD),
                bannerAdLoadListener
        );
    }


    protected void addUiLogs(final String... logs) {
        Collections.addAll(backingUiLogs, logs);
        uiLogs.postValue(new ArrayList<>(backingUiLogs));
    }

    public void addToUiLogs(final String... logs) {
        addUiLogs(logs);
    }

    public void clearUiLogs() {
        backingUiLogs.clear();
        uiLogs.setValue(new ArrayList<>());
        addUiLogs("UI Logs Cleared.");
    }

    protected ChartboostMediationFullscreenAdListener createFullscreenAdListener(
            final String placementName,
            final FullscreenAdLifecycleListener customAdListener,
            @Nullable final ChartboostMediationFullscreenAdQueue queue
    ) {
        return new ChartboostMediationFullscreenAdListener() {
            @Override
            public void onAdClicked(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLogs(String.format("%s ad clicked", placementName));
            }

            @Override
            public void onAdClosed(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, @Nullable ChartboostMediationAdException e) {
                customAdListener.onAdClosed(placementName, chartboostMediationFullscreenAd, e, queue == null ? null : queue.hasNextAd());
                addUiLogs(String.format("%s ad closed", placementName));
            }

            @Override
            public void onAdRewarded(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLogs(String.format("%s ad rewarded", placementName));
            }

            @Override
            public void onAdImpressionRecorded(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLogs(String.format("%s impression recorded", placementName));
            }

            @Override
            public void onAdExpired(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLogs(String.format("%s ad expired", placementName));
            }
        };
    }

    protected ChartboostMediationFullscreenAdShowListener createFullscreenAdShowListener(
            final String placementName,
            final FullscreenAdLifecycleListener customAdListener,
            @Nullable final ChartboostMediationFullscreenAdQueue queue
    ) {
        return new ChartboostMediationFullscreenAdShowListener() {
            @Override
            public void onAdShown(@NonNull ChartboostMediationAdShowResult chartboostMediationAdShowResult) {
                if (chartboostMediationAdShowResult.getError() != null) {
                    addUiLogs(String.format("%s fullscreen ad failed to show with error code %s: %s",
                            placementName,
                            chartboostMediationAdShowResult.getError().getCode(),
                            chartboostMediationAdShowResult.getError().getMessage()));
                    customAdListener.onAdShowFailure(placementName, queue == null ? null : queue.hasNextAd());
                } else {
                    customAdListener.onAdShowSuccess(placementName, queue == null ? null : queue.getNumberOfAdsReady());
                }
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
