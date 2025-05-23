package com.example.chartboost.mediation.sdk.demo.java.queuedads;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chartboost.chartboostmediationsdk.ad.AdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationAdShowResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdViewListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueue;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueManager;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdShowListener;
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class QueuedAdsViewModel extends ViewModel {

    private Boolean isSetup = false;
    private String interstitialPlacement = "";
    private String rewardedPlacement = "";
    private static final int DEFAULT_QUEUE_CAPACITY = 5;

    private final MutableLiveData<QueuedAdsUIState> _uiState = new MutableLiveData<>(QueuedAdsUIState.initialState());
    public final LiveData<QueuedAdsUIState> uiState = _uiState;

    private final MutableLiveData<List<String>> _uiLogs = new MutableLiveData<>(new ArrayList<>());
    public final LiveData<List<String>> logs = _uiLogs;

    private final Map<String, ChartboostMediationFullscreenAdQueue> queues = new HashMap<>();


    public void setup(String interstitialPlacement, String rewardedPlacement) {
        if (!isSetup) {
            this.interstitialPlacement = interstitialPlacement;
            this.rewardedPlacement = rewardedPlacement;
        }
    }

    final public void createAdQueue(Context context) {
        createAdQueueForPlacement(interstitialPlacement, context);
        createAdQueueForPlacement(rewardedPlacement, context);
    }

    final public void startStopQueue(String placement) {
        QueueControlState newState = updateQueueControlButtonState(placement);
        if (newState == QueueControlState.STOP) {
            queues.get(placement).start();
            addUiLogs(String.format("Queue for %s started", placement));
        } else {
            queues.get(placement).stop();
            addUiLogs(String.format("Queue for %s stopped", placement));
        }
    }

    public void clearUiLogs() {
        _uiLogs.postValue(Collections.emptyList());
    }
    public void addToUiLogs(String log){
        addUiLogs(log);
    }

    final public void showFullscreenAd(
            String placementName,
            Activity activity
    ) {
        ChartboostMediationFullscreenAd ad = null;
        ChartboostMediationFullscreenAdQueue queue = queues.get(placementName);
        if (queue.hasNextAd()) {
            addUiLogs("Using queue to show the ad");
            ad = queue.getNextAd();
            if (ad != null) {
                // Be mindful that ads will need to be reattached to your ChartboostMediationFullscreenAdListener if you are listening to ad cycle events.
                ad.setListener(createFullscreenAdListener(
                        placementName,
                        /* onAdClosed */() -> updateShowButtonState.accept(placementName, queue.hasNextAd())
                ));
                addUiLogs("Fullscreen ad is about to show");
                ad.showFullscreenAdFromJava(activity, createFullscreenAdShowListener(
                        placementName,
                        /* onAdShownFailure */() -> updateShowButtonState.accept(placementName, queue.hasNextAd()),
                        /* onAdShownSuccess */() -> addUiLogs(
                                String.format("%s fullscreen ad shown", placementName),
                                String.format("Fullscreen ad queue has been updated for placement %s. One ad has been removed after onShow. Number of ads ready to show: %s", placementName, queue.getNumberOfAdsReady()))
                ));
            } else {
                addUiLogs("Ad retrieved from the queue is null");
            }
        } else {
            addUiLogs("No ads in the queue. Load an ad first.");
            updateShowButtonState.accept(placementName, false);
        }
    }

    private void createAdQueueForPlacement(String placement, Context context) {
        ChartboostMediationFullscreenAdQueue queue = ChartboostMediationFullscreenAdQueueManager.queue(context, placement);
        queue.setQueueCapacity(DEFAULT_QUEUE_CAPACITY);// this will update queue capacity only to the maximum capacity received on initialization
        queue.setAdQueueListener(createFullscreenAdQueueListener(placement, (enabled) -> updateShowButtonState.accept(placement, enabled)));
        queues.put(placement, queue);
    }

    private BiConsumer<String, Boolean> updateShowButtonState = (placementName, isEnabled) -> {
        if (Objects.equals(placementName, interstitialPlacement)) {
            _uiState.postValue(_uiState.getValue().withIsShowInterstitialButtonEnabled(isEnabled));
            return;
        }
        if (Objects.equals(placementName, rewardedPlacement)) {
            _uiState.postValue(_uiState.getValue().withIsShowRewardedButtonEnabled(isEnabled));
        }
    };

    private QueueControlState updateQueueControlButtonState(String placementName) {
        QueuedAdsUIState currentState = _uiState.getValue();
        QueueControlState nextState = null;
        if (placementName.equals(interstitialPlacement)) {
            nextState = getNextState(currentState.interstitialQueueControlState);
            _uiState.postValue(currentState.withInterstitialQueueControlState(nextState));
            return nextState;
        }
        if (placementName.equals(rewardedPlacement)) {
            nextState = getNextState(currentState.rewardedQueueControlState);
            _uiState.postValue(currentState.withRewardedQueueControlState(nextState));
            return nextState;
        }
        return null;
    }

    private QueueControlState getNextState(QueueControlState currentState) {
        if (currentState == QueueControlState.START) {
            return QueueControlState.STOP;
        } else {
            return QueueControlState.START;
        }
    }

    private ChartboostMediationFullscreenAdQueueListener createFullscreenAdQueueListener(
            String placementName,
            Consumer<Boolean> enableShowButtonConsumer) {
        return new ChartboostMediationFullscreenAdQueueListener() {
            @Override
            public void onFullScreenAdQueueUpdated(@NonNull ChartboostMediationFullscreenAdQueue queue, @NonNull AdLoadResult adLoadResult, int numberOfAdsReady) {
                addUiLogs("Fullscreen ad queue has been updated for placement " + placementName + ". Number of ads ready to show: " + numberOfAdsReady);
                enableShowButtonConsumer.accept(numberOfAdsReady > 0);
            }

            @Override
            public void onFullscreenAdQueueExpiredAdRemoved(@NonNull ChartboostMediationFullscreenAdQueue adQueue, int numberOfAdsReady) {
                addUiLogs("Fullscreen ad queue expired ad has been removed for placement " + placementName + ". Number of ads ready to show: " + numberOfAdsReady);
                enableShowButtonConsumer.accept(numberOfAdsReady > 0);
            }
        };
    }

    private ChartboostMediationFullscreenAdShowListener createFullscreenAdShowListener(
            String placementName,
            Runnable onAdShownFailure,
            Runnable onAdShownSuccess
    ) {
        return new ChartboostMediationFullscreenAdShowListener() {
            @Override
            public void onAdShown(@NonNull ChartboostMediationAdShowResult chartboostMediationAdShowResult) {
                if (chartboostMediationAdShowResult.getError() != null) {
                    addUiLogs(String.format("%s fullscreen ad failed to show with error: %s", placementName, chartboostMediationAdShowResult.getError().getCause()));
                    onAdShownFailure.run();
                } else {
                    onAdShownSuccess.run();
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
                addUiLogs(String.format("%s ad clicked", placementName));
            }

            @Override
            public void onAdClosed(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, @Nullable ChartboostMediationAdException e) {
                addUiLogs(String.format("%s ad closed", placementName));
                onAdClosed.run();
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

    private void addUiLogs(String... logs) {
        List<String> updatedLogs = new ArrayList<>(_uiLogs.getValue());
        updatedLogs.addAll(Arrays.stream(logs).collect(Collectors.toList()));
        _uiLogs.postValue(updatedLogs);
    }

    public final ChartboostMediationBannerAdViewListener bannerAdListener =
            new ChartboostMediationBannerAdViewListener() {
                @Override
                public void onAdClicked(@NonNull String s) {
                    addUiLogs("Banner clicked");
                }

                @Override
                public void onAdImpressionRecorded(@NonNull String s) {
                    addUiLogs("Banner impression recorded");
                }

                @Override
                public void onAdViewAdded(@NonNull String s, @Nullable View view) {
                    addUiLogs("Ad view added");
                }
            };

    public final ChartboostMediationBannerAdLoadListener bannerAdLoadListener = new ChartboostMediationBannerAdLoadListener() {
        @Override
        public void onAdLoaded(@NonNull ChartboostMediationBannerAdLoadResult chartboostMediationBannerAdLoadResult) {
            if (chartboostMediationBannerAdLoadResult.getError() == null) {
                addUiLogs("Banner loaded");
            } else {
                addUiLogs(String.format("Error loading banner ad , code %s : ", chartboostMediationBannerAdLoadResult.getError()));
            }
        }
    };

    @Override
    protected void onCleared() {
        for (ChartboostMediationFullscreenAdQueue queue : queues.values()) {
            queue.stop();
            while (queue.hasNextAd()) {
                queue.getNextAd().invalidate();
            }
        }
    }

}
