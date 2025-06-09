package com.example.chartboost.mediation.sdk.demo.java.manualload;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadRequest;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdShowListener;
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException;
import com.chartboost.chartboostmediationsdk.domain.Keywords;
import com.example.chartboost.mediation.sdk.demo.java.BaseAdsViewModel;
import com.example.chartboost.mediation.sdk.demo.java.FullscreenAdLifecycleListener;
import com.example.chartboost.mediation.sdk.demo.java.Event;

import java.util.HashMap;
import java.util.function.Consumer;

public class ManualLoadViewModel extends BaseAdsViewModel implements FullscreenAdLifecycleListener {

    private ChartboostMediationFullscreenAd interstitialAd;
    private ChartboostMediationFullscreenAd rewardedAd;
    private final MutableLiveData<ManualLoadUIStateModel> uiState = new MutableLiveData(ManualLoadUIStateModel.initialState());

    public final LiveData<ManualLoadUIStateModel> getUiState() {
        return uiState;
    }

    private final MutableLiveData<Event<ChartboostMediationFullscreenAd>> showAdEvent = new MutableLiveData<>();

    public final LiveData<Event<ChartboostMediationFullscreenAd>> getShowAdEvent() {
        return showAdEvent;
    }

    private void updateState(ManualLoadUIStateModel value) {
        uiState.postValue(value);
    }

    private @Nullable ManualLoadUIStateModel getState() {
        return uiState.getValue();
    }

    public void loadInterstitial(final Context context) {
        updateState(getState().withInterstitialLoadEnabled(false));
        addUiLogs(String.format("Loading %s ad", interstitialPlacement));
        loadAd(
                interstitialPlacement,
                this,
                context
        );
    }

    public void loadRewarded(final Context context) {
        updateState(getState().withRewardedLoadEnabled(false));
        addUiLogs(String.format("Loading %s ad", rewardedPlacement));
        loadAd(
                rewardedPlacement,
                this,
                context
        );
    }

    public void showInterstitial() {
        addUiLogs(String.format("Show %s ad clicked", interstitialPlacement));
        if (interstitialAd != null) {
            showAdEvent.postValue(new Event<>(interstitialAd));
        } else {
            addUiLogs("Interstitial ad is null. Load an ad first.");
            updateState(getState().withInterstitialShowEnabled(false));
        }
    }

    public void showRewarded() {
        addUiLogs(String.format("Show %s ad clicked", rewardedPlacement));
        if (rewardedAd != null) {
            showAdEvent.postValue(new Event<>(rewardedAd));
        } else {
            addUiLogs("Rewarded ad is null. Load an ad first.");
            updateState(getState().withRewardedShowEnabled(false));
        }
    }

    private void loadAd(
            final String placementName,
            final FullscreenAdLifecycleListener customAdListener,
            final Context context
    ) {
        ChartboostMediationFullscreenAdLoadRequest request = new ChartboostMediationFullscreenAdLoadRequest(placementName, new Keywords(), new HashMap<>());
        ChartboostMediationFullscreenAd.loadFullscreenAdFromJava(
                context,
                request,
                createFullscreenAdListener(
                        placementName,
                        customAdListener,
                        null
                ),
                createFullscreenAdLoadListener(
                        placementName,
                        customAdListener
                )
        );
    }

    public ChartboostMediationFullscreenAdShowListener getFullscreenAdShowListener(final String placementName) {
        if (placementName.equals(interstitialPlacement)) {
            return createFullscreenAdShowListener(
                    interstitialPlacement,
                    this,
                    null
            );
        }
        if (placementName.equals(rewardedPlacement)) {
            return createFullscreenAdShowListener(
                    rewardedPlacement,
                    this,
                    null
            );
        }
        return null;
    }

    private ChartboostMediationFullscreenAdLoadListener createFullscreenAdLoadListener2(
            final String placementName,
            final Consumer<ChartboostMediationFullscreenAd> onAdLoaded
    ) {
        return new ChartboostMediationFullscreenAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull ChartboostMediationFullscreenAdLoadResult chartboostMediationFullscreenAdLoadResult) {
                if (chartboostMediationFullscreenAdLoadResult.getError() == null) {
                    addUiLogs(String.format("%s fullscreen ad loaded, loadId %s", placementName, chartboostMediationFullscreenAdLoadResult.getLoadId()));
                    onAdLoaded.accept(chartboostMediationFullscreenAdLoadResult.getAd());
                } else {
                    addUiLogs(String.format("Error loading %s ad , code %s : ", placementName, chartboostMediationFullscreenAdLoadResult.getError()));
                    onAdLoaded.accept(null);
                }
            }
        };
    }

    private ChartboostMediationFullscreenAdLoadListener createFullscreenAdLoadListener(
            final String placementName,
            final FullscreenAdLifecycleListener adListener
    ) {
        return new ChartboostMediationFullscreenAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull ChartboostMediationFullscreenAdLoadResult chartboostMediationFullscreenAdLoadResult) {
                adListener.onAdLoaded(placementName, chartboostMediationFullscreenAdLoadResult);
            }
        };
    }

    @Override
    protected void onCleared() {
        if (interstitialAd != null) {
            interstitialAd.invalidate();
        }
        if (rewardedAd != null) {
            rewardedAd.invalidate();
        }
    }

    @Override
    public void onAdLoaded(final String placementName, final ChartboostMediationFullscreenAdLoadResult adLoadResult) {
        if (adLoadResult.getError() == null) {
            addUiLogs(String.format("%s fullscreen ad loaded, loadId %s", placementName, adLoadResult.getLoadId()));
            assignToProperAd(placementName, adLoadResult.getAd());
            updateButtons(placementName, true, true);
        } else {
            addUiLogs(String.format("Error loading %s ad , code %s : ", placementName, adLoadResult.getError()));
            updateButtons(placementName, true, false);
        }
    }

    @Override
    public void onAdClosed(final String placementName, final @NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, final @Nullable ChartboostMediationAdException e, @Nullable Boolean hasNextAdQueued) {
        updateButtons(placementName, true, false);
    }

    @Override
    public void onAdShowFailure(final String placementName, final @Nullable Boolean hasAdQueued) {
        updateButtons(placementName, true, false);
    }

    @Override
    public void onAdShowSuccess(final String placementName, final @Nullable Integer numberOfAdsReady) {
        addUiLogs(String.format("%s fullscreen ad shown", rewardedPlacement));
    }

    private void assignToProperAd(final String placementName, final ChartboostMediationFullscreenAd ad) {
        if (placementName.equals(interstitialPlacement)) {
            interstitialAd = ad;
        }
        if (placementName.equals(rewardedPlacement)) {
            rewardedAd = ad;
        }
    }

    private void updateButtons(final String placementName, final boolean loadEnabled, final boolean showEnabled) {
        if (placementName.equals(interstitialPlacement)) {
            updateState(getState()
                    .withInterstitialLoadEnabled(loadEnabled)
                    .withInterstitialShowEnabled(showEnabled)
            );
        }
        if (placementName.equals(rewardedPlacement)) {
            updateState(getState()
                    .withRewardedLoadEnabled(loadEnabled)
                    .withRewardedShowEnabled(showEnabled)
            );
        }

    }
}
