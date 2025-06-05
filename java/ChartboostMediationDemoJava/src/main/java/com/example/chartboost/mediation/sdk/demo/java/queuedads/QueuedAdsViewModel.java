package com.example.chartboost.mediation.sdk.demo.java.queuedads;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chartboost.chartboostmediationsdk.ad.AdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueue;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueManager;
import com.example.chartboost.mediation.sdk.demo.java.BaseAdsViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class QueuedAdsViewModel extends BaseAdsViewModel {

    private static final int DEFAULT_QUEUE_CAPACITY = 5;

    private final MutableLiveData<QueuedAdsUIState> _uiState = new MutableLiveData<>(QueuedAdsUIState.initialState());
    public final LiveData<QueuedAdsUIState> uiState = _uiState;

    private final Map<String, ChartboostMediationFullscreenAdQueue> queues = new HashMap<>();

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
