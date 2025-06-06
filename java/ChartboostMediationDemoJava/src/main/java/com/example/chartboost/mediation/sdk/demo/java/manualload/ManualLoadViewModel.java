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
import com.chartboost.chartboostmediationsdk.domain.Keywords;
import com.example.chartboost.mediation.sdk.demo.java.BaseAdsViewModel;
import com.example.chartboost.mediation.sdk.demo.java.Event;

import java.util.HashMap;
import java.util.function.Consumer;

public class ManualLoadViewModel extends BaseAdsViewModel {

    private ChartboostMediationFullscreenAd interstitialAd;
    private ChartboostMediationFullscreenAd rewardedAd;
    private final MutableLiveData<ManualLoadUIStateModel> _uiState = new MutableLiveData(ManualLoadUIStateModel.initialState());

    private final MutableLiveData<Event<ChartboostMediationFullscreenAd>> _showAdEvent = new MutableLiveData<>();
    public final LiveData<Event<ChartboostMediationFullscreenAd>> showAdEvent = _showAdEvent;

    public LiveData<ManualLoadUIStateModel> uiState = _uiState;

    private void updateState(ManualLoadUIStateModel value) {
        _uiState.postValue(value);
    }

    private @Nullable ManualLoadUIStateModel getState() {
        return _uiState.getValue();
    }

    public void loadInterstitial(final Context context) {
        updateState(getState().withInterstitialLoadEnabled(false));
        addUiLogs(String.format("Loading %s ad", interstitialPlacement));
        loadAd(
                interstitialPlacement,
                /* onAdClosed */ () -> updateState(getState().withInterstitialShowEnabled(false)),
                /* onAdLoaded */ (ad) -> {
                    if (ad == null) {
                        updateState(getState()
                                .withInterstitialLoadEnabled(true)
                                .withInterstitialShowEnabled(false));
                    } else {
                        interstitialAd = ad;
                        updateState(getState()
                                .withInterstitialLoadEnabled(true)
                                .withInterstitialShowEnabled(true));
                    }
                },
                context
        );
    }

    public void loadRewarded(final Context context) {
        updateState(getState().withRewardedLoadEnabled(false));
        addUiLogs(String.format("Loading %s ad", rewardedPlacement));
        loadAd(
                rewardedPlacement,
                /* onAdClosed */ () -> updateState(getState().withRewardedShowEnabled(false)),
                /* onAdLoaded */ (ad) -> {
                    if (ad == null) {
                        updateState(getState()
                                .withRewardedLoadEnabled(true)
                                .withRewardedShowEnabled(false));
                    } else {
                        rewardedAd = ad;
                        updateState(getState()
                                .withRewardedLoadEnabled(true)
                                .withRewardedShowEnabled(true));
                    }
                },
                context
        );
    }

    public void showInterstitial() {
        addUiLogs(String.format("Show %s ad clicked", interstitialPlacement));
        if (interstitialAd != null) {
            _showAdEvent.postValue(new Event<>(interstitialAd));
        } else {
            addUiLogs("Interstitial ad is null. Load an ad first.");
            updateState(getState().withInterstitialShowEnabled(false));
        }
    }

    public void showRewarded() {
        addUiLogs(String.format("Show %s ad clicked", rewardedPlacement));
        if (rewardedAd != null) {
            _showAdEvent.postValue(new Event<>(rewardedAd));
        } else {
            addUiLogs("Rewarded ad is null. Load an ad first.");
            updateState(getState().withRewardedShowEnabled(false));
        }
    }

    private void loadAd(final String placementName, final Runnable onAdClosed, final Consumer<ChartboostMediationFullscreenAd> onAdLoaded, final Context context) {
        ChartboostMediationFullscreenAdLoadRequest request = new ChartboostMediationFullscreenAdLoadRequest(placementName, new Keywords(), new HashMap<>());
        ChartboostMediationFullscreenAd.loadFullscreenAdFromJava(
                context,
                request,
                createFullscreenAdListener(
                        placementName,
                        /* onAdClosed */ () -> onAdClosed.run()
                ),
                createFullscreenAdLoadListener(
                        placementName,
                        /* onAdLoaded */ (ad) -> onAdLoaded.accept(ad)
                )
        );
    }

    public ChartboostMediationFullscreenAdShowListener getFullscreenAdShowListener(final String placementName) {
        if (placementName.equals(interstitialPlacement)) {
            return createFullscreenAdShowListener(
                    interstitialPlacement,
                    /* onAdShownFailure */() -> updateState(getState().withInterstitialShowEnabled(false)),
                    /* onAdShownSuccess */() -> addUiLogs(String.format("%s fullscreen ad shown", interstitialPlacement))
            );
        }
        if (placementName.equals(rewardedPlacement)) {
            return createFullscreenAdShowListener(
                    rewardedPlacement,
                    /* onAdShownFailure */() -> updateState(getState().withRewardedShowEnabled(false)),
                    /* onAdShownSuccess */() -> addUiLogs(String.format("%s fullscreen ad shown", rewardedPlacement))
            );
        }
        return null;
    }

    private ChartboostMediationFullscreenAdLoadListener createFullscreenAdLoadListener(
            final String placementName,
            final Consumer<ChartboostMediationFullscreenAd> onAdLoaded
    ) {
        return new ChartboostMediationFullscreenAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull ChartboostMediationFullscreenAdLoadResult chartboostMediationFullscreenAdLoadResult) {
                if (chartboostMediationFullscreenAdLoadResult.getError() == null) {
                    addUiLogs(String.format("%s fullscreen ad loaded", placementName));
                    onAdLoaded.accept(chartboostMediationFullscreenAdLoadResult.getAd());
                } else {
                    addUiLogs(String.format("Error loading %s ad , code %s : ", placementName, chartboostMediationFullscreenAdLoadResult.getError()));
                    onAdLoaded.accept(null);
                }
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
}
