package com.example.chartboost.mediation.sdk.demo.java;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationAdShowResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdViewListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdShowListener;
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseAdsViewModel extends ViewModel {

    protected Boolean isSetup = false;
    protected String interstitialPlacement = "";
    protected String rewardedPlacement = "";

    private final MutableLiveData<List<String>> _uiLogs = new MutableLiveData<>(new ArrayList<>());
    public final LiveData<List<String>> uiLogs = _uiLogs;

    public final ChartboostMediationBannerAdViewListener bannerAdListener;
    public final ChartboostMediationBannerAdLoadListener bannerAdLoadListener;

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

    public void setup(final String interstitialPlacement, final String rewardedPlacement) {
        if (!isSetup) {
            this.interstitialPlacement = interstitialPlacement;
            this.rewardedPlacement = rewardedPlacement;
            isSetup = true;
        }
    }

    protected void addUiLogs(final String... logs) {
        List<String> updatedLogs = new ArrayList<>(_uiLogs.getValue());
        updatedLogs.addAll(Arrays.stream(logs).collect(Collectors.toList()));
        _uiLogs.postValue(updatedLogs);
    }

    public void addToUiLogs(final String... logs) {
        addUiLogs(logs);
    }

    public void clearUiLogs() {
        _uiLogs.setValue(new ArrayList<>());
        addUiLogs("UI Logs Cleared.");
    }

    protected ChartboostMediationFullscreenAdListener createFullscreenAdListener(
            final String placementName,
            final Runnable onAdClosed
    ) {
        return new ChartboostMediationFullscreenAdListener() {
            @Override
            public void onAdClicked(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLogs(String.format("%s ad clicked", placementName));
            }

            @Override
            public void onAdClosed(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, @Nullable ChartboostMediationAdException e) {
                addUiLogs(String.format("%s ad closed", placementName));
                if (onAdClosed != null) {
                    onAdClosed.run();
                }
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
            final Runnable onAdShownFailure,
            final Runnable onAdShownSuccess
    ) {
        return new ChartboostMediationFullscreenAdShowListener() {
            @Override
            public void onAdShown(@NonNull ChartboostMediationAdShowResult chartboostMediationAdShowResult) {
                if (chartboostMediationAdShowResult.getError() != null) {
                    addUiLogs(String.format("%s fullscreen ad failed to show with error code %s: %s",
                            placementName,
                            chartboostMediationAdShowResult.getError().getCode(),
                            chartboostMediationAdShowResult.getError().getMessage()));
                    if (onAdShownFailure != null) {
                        onAdShownFailure.run();
                    }
                } else {
                    if (onAdShownSuccess != null) {
                        onAdShownSuccess.run();
                    }
                }
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
