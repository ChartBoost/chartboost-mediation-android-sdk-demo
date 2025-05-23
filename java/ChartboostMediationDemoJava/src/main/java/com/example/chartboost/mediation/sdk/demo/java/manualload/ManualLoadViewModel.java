package com.example.chartboost.mediation.sdk.demo.java.manualload;

import android.content.Context;
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
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadRequest;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdShowListener;
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException;
import com.chartboost.chartboostmediationsdk.domain.Keywords;
import com.example.chartboost.mediation.sdk.demo.java.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ManualLoadViewModel extends ViewModel {

    private Boolean isSetup = false;
    private String interstitialPlacement = "";
    private String rewardedPlacement = "";
    private ChartboostMediationFullscreenAd interstitialAd;
    private ChartboostMediationFullscreenAd rewardedAd;
    private MutableLiveData<ManualLoadUIStateModel> _uiState = new MutableLiveData(ManualLoadUIStateModel.initialState());
    private MutableLiveData<List<String>> _uiLogs = new MutableLiveData(Collections.emptyList());

    public void setup(String interstitialPlacement, String rewardedPlacement) {
        if (!isSetup) {
            this.interstitialPlacement = interstitialPlacement;
            this.rewardedPlacement = rewardedPlacement;
        }
    }

    private final MutableLiveData<Event<ChartboostMediationFullscreenAd>> _showAdEvent = new MutableLiveData<>();
    public final LiveData<Event<ChartboostMediationFullscreenAd>> showAdEvent = _showAdEvent;

    public LiveData<ManualLoadUIStateModel> uiState = _uiState;
    public LiveData<List<String>> uiLogs = _uiLogs;

    private void updateState(ManualLoadUIStateModel value) {
        _uiState.postValue(value);
    }

    private @Nullable ManualLoadUIStateModel getState() {
        return _uiState.getValue();
    }

    public void loadInterstitial(Context context) {
        updateState(getState().withInterstitialLoadEnabled(false));
        addUiLog(String.format("Loading %s ad", interstitialPlacement));
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

    public void loadRewarded(Context context) {
        updateState(getState().withRewardedLoadEnabled(false));
        addUiLog(String.format("Loading %s ad", rewardedPlacement));
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
        addUiLog(String.format("Show %s ad clicked", interstitialPlacement));
        if (interstitialAd != null) {
            _showAdEvent.postValue(new Event<>(interstitialAd));
        } else {
            addUiLog("Interstitial ad is null. Load an ad first.");
            updateState(getState().withInterstitialShowEnabled(false));
        }
    }

    public void showRewarded() {
        addUiLog(String.format("Show %s ad clicked", rewardedPlacement));
        if (rewardedAd != null) {
            _showAdEvent.postValue(new Event<>(rewardedAd));
        } else {
            addUiLog("Rewarded ad is null. Load an ad first.");
            updateState(getState().withRewardedShowEnabled(false));
        }
    }

    private void loadAd(String placementName, Runnable onAdClosed, Consumer<ChartboostMediationFullscreenAd> onAdLoaded, Context context) {
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

    public ChartboostMediationFullscreenAdShowListener getFullscreenAdShowListener(String placementName) {
        if (placementName == interstitialPlacement) {
            return createFullscreenAdShowListener(interstitialPlacement, () -> updateState(getState().withInterstitialShowEnabled(false)));
        }
        if (placementName == rewardedPlacement) {
            return createFullscreenAdShowListener(rewardedPlacement, () -> updateState(getState().withRewardedShowEnabled(false)));
        }
        return null;
    }

    public void clearUiLogs() {
        _uiLogs.postValue(Collections.emptyList());
    }

    public void addToUiLogs(String log){
        addUiLog(log);
    }

    private void addUiLog(String log) {
        List<String> updatedLogs = new ArrayList<>(_uiLogs.getValue());
        updatedLogs.add(log);
        _uiLogs.postValue(updatedLogs);
    }

    private ChartboostMediationFullscreenAdLoadListener createFullscreenAdLoadListener(
            String placementName,
            Consumer<ChartboostMediationFullscreenAd> onAdLoaded
    ) {
        return new ChartboostMediationFullscreenAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull ChartboostMediationFullscreenAdLoadResult chartboostMediationFullscreenAdLoadResult) {
                if (chartboostMediationFullscreenAdLoadResult.getError() == null) {
                    addUiLog(String.format("%s fullscreen ad loaded", placementName));
                    onAdLoaded.accept(chartboostMediationFullscreenAdLoadResult.getAd());
                } else {
                    addUiLog(String.format("Error loading %s ad , code %s : ", placementName, chartboostMediationFullscreenAdLoadResult.getError()));
                    onAdLoaded.accept(null);
                }
            }
        };
    }

    private ChartboostMediationFullscreenAdListener createFullscreenAdListener(
            String placementName,
            Runnable onAdClosed
    ) {
        return new ChartboostMediationFullscreenAdListener() {

            @Override
            public void onAdClicked(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLog(String.format("%s ad clicked", placementName));
            }

            @Override
            public void onAdClosed(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, @Nullable ChartboostMediationAdException e) {
                addUiLog(String.format("%s ad closed", placementName));
                onAdClosed.run();
            }

            @Override
            public void onAdRewarded(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLog(String.format("%s ad rewarded", placementName));

            }

            @Override
            public void onAdImpressionRecorded(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLog(String.format("%s impression recorded", placementName));

            }

            @Override
            public void onAdExpired(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                addUiLog(String.format("%s ad expired", placementName));

            }
        };
    }

    private ChartboostMediationFullscreenAdShowListener createFullscreenAdShowListener(
            String placementName,
            Runnable onAdShownFailure
    ) {
        return new ChartboostMediationFullscreenAdShowListener() {
            @Override
            public void onAdShown(@NonNull ChartboostMediationAdShowResult chartboostMediationAdShowResult) {
                if (chartboostMediationAdShowResult.getError() != null) {
                    addUiLog(String.format("%s fullscreen ad failed to show with error: %s", placementName, chartboostMediationAdShowResult.getError().getCause()));
                    onAdShownFailure.run();
                } else {
                    addUiLog(String.format("%s fullscreen ad shown", placementName));
                }
            }
        };
    }

    public final ChartboostMediationBannerAdViewListener bannerAdListener =
            new ChartboostMediationBannerAdViewListener() {
                @Override
                public void onAdClicked(@NonNull String s) {
                    addUiLog("Banner clicked");
                }

                @Override
                public void onAdImpressionRecorded(@NonNull String s) {
                    addUiLog("Banner impression recorded");
                }

                @Override
                public void onAdViewAdded(@NonNull String s, @Nullable View view) {
                    addUiLog("Ad view added");
                }
            };

    public final ChartboostMediationBannerAdLoadListener bannerAdLoadListener = new ChartboostMediationBannerAdLoadListener() {
        @Override
        public void onAdLoaded(@NonNull ChartboostMediationBannerAdLoadResult chartboostMediationBannerAdLoadResult) {
            if (chartboostMediationBannerAdLoadResult.getError() == null) {
                addUiLog("Banner loaded");
            } else {
                addUiLog(String.format("Error loading banner ad , code %s : ", chartboostMediationBannerAdLoadResult.getError()));
            }
        }
    };


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
