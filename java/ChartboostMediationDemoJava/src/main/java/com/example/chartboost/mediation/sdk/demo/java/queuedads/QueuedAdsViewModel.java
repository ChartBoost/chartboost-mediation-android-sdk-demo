package com.example.chartboost.mediation.sdk.demo.java.queuedads;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chartboost.chartboostmediationsdk.ad.AdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueue;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueManager;
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException;
import com.example.chartboost.mediation.sdk.demo.java.BaseAdsViewModel;
import com.example.chartboost.mediation.sdk.demo.java.FullscreenAdLifecycleListener;

import java.util.HashMap;
import java.util.Map;

public class QueuedAdsViewModel extends BaseAdsViewModel implements FullscreenAdLifecycleListener, ViewModelAdQueueListener {

    /**
     * The current capacity of the queue.
     * <p>
     * Note: When setting this value, it cannot exceed the maximum capacity
     * established during initialization. It will be capped if a larger value is provided.
     */
    private static final int DEFAULT_QUEUE_CAPACITY = 5;

    private final MutableLiveData<QueuedAdsUIState> uiState = new MutableLiveData<>(QueuedAdsUIState.initialState());

    public final LiveData<QueuedAdsUIState> getUiState() {
        return uiState;
    }

    private final Map<String, ChartboostMediationFullscreenAdQueue> queues = new HashMap<>();

    final public void createAdQueue(final Context context) {
        createAdQueueForPlacement(interstitialPlacement, context);
        createAdQueueForPlacement(rewardedPlacement, context);
    }

    final public void startStopQueue(final String placement) {
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
            final String placementName,
            final Activity activity
    ) {
        ChartboostMediationFullscreenAd ad;
        ChartboostMediationFullscreenAdQueue queue = queues.get(placementName);
        if (queue.hasNextAd()) {
            addUiLogs("Using queue to show the ad");
            ad = queue.getNextAd();
            if (ad != null) {
                // Be mindful that ads will need to be reattached to your ChartboostMediationFullscreenAdListener if you are listening to ad cycle events.
                ad.setListener(createFullscreenAdListener(
                        placementName,
                        this,
                        queue
                ));
                addUiLogs("Fullscreen ad is about to show");
                ad.showFullscreenAdFromJava(
                        activity,
                        createFullscreenAdShowListener(
                                placementName,
                                this,
                                queue)
                );
            } else {
                addUiLogs("Ad retrieved from the queue is null");
            }
        } else {
            addUiLogs("No ads in the queue. Load an ad first.");
            updateShowButtonState(placementName, false);
        }
    }

    private void createAdQueueForPlacement(final String placement, final Context context) {
        ChartboostMediationFullscreenAdQueue queue = ChartboostMediationFullscreenAdQueueManager.queue(context, placement);
        queue.setQueueCapacity(DEFAULT_QUEUE_CAPACITY);// this will update queue capacity only to the maximum capacity received on initialization
        queue.setAdQueueListener(createFullscreenAdQueueListener(placement, this));
        queues.put(placement, queue);
    }

    private void updateShowButtonState(final String placementName, final boolean isEnabled) {
        if (placementName == null) {
            return;
        }

        if (placementName.equals(interstitialPlacement)) {
            uiState.postValue(uiState.getValue().withIsShowInterstitialButtonEnabled(isEnabled));
        } else if (placementName.equals(rewardedPlacement)) {
            uiState.postValue(uiState.getValue().withIsShowRewardedButtonEnabled(isEnabled));
        }
    }

    private QueueControlState updateQueueControlButtonState(final String placementName) {
        QueuedAdsUIState currentState = uiState.getValue();
        QueueControlState nextState;
        if (placementName.equals(interstitialPlacement)) {
            nextState = getNextState(currentState.interstitialQueueControlState);
            uiState.postValue(currentState.withInterstitialQueueControlState(nextState));
            return nextState;
        }
        if (placementName.equals(rewardedPlacement)) {
            nextState = getNextState(currentState.rewardedQueueControlState);
            uiState.postValue(currentState.withRewardedQueueControlState(nextState));
            return nextState;
        }
        return null;
    }

    private QueueControlState getNextState(final QueueControlState currentState) {
        if (currentState == QueueControlState.START) {
            return QueueControlState.STOP;
        } else {
            return QueueControlState.START;
        }
    }

    private ChartboostMediationFullscreenAdQueueListener createFullscreenAdQueueListener(
            final String placementName,
            final ViewModelAdQueueListener adQueueListener) {
        return new ChartboostMediationFullscreenAdQueueListener() {
            @Override
            public void onFullScreenAdQueueUpdated(@NonNull ChartboostMediationFullscreenAdQueue queue, @NonNull AdLoadResult adLoadResult, int numberOfAdsReady) {
                addUiLogs("Fullscreen ad queue has been updated for placement " + placementName + ". Number of ads ready to show: " + numberOfAdsReady);
                adQueueListener.onFullScreenAdQueueUpdated(placementName, numberOfAdsReady);
            }

            @Override
            public void onFullscreenAdQueueExpiredAdRemoved(@NonNull ChartboostMediationFullscreenAdQueue adQueue, int numberOfAdsReady) {
                addUiLogs("Fullscreen ad queue expired ad has been removed for placement " + placementName + ". Number of ads ready to show: " + numberOfAdsReady);
                adQueueListener.onFullScreenAdQueueUpdated(placementName, numberOfAdsReady);
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

    @Override
    public void onAdLoaded(final String placementName, final ChartboostMediationFullscreenAdLoadResult adLoadResult) {
        // ChartboostMediationFullscreenAdQueueListener logs about loaded ads, and updates the buttons. No additional logic to show.
    }

    @Override
    public void onAdClosed(final String placementName, final @NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, final @Nullable ChartboostMediationAdException e, final @Nullable Boolean hasAdQueued) {
        updateShowButtonState(placementName, hasAdQueued);
    }

    @Override
    public void onAdShowFailure(final String placementName, final @Nullable Boolean hasAdQueued) {
        updateShowButtonState(placementName, hasAdQueued == null ? false : hasAdQueued);
    }

    @Override
    public void onAdShowSuccess(String placementName, @Nullable Integer numberOfAdsReady) {
        addUiLogs(
                String.format("%s fullscreen ad shown", placementName),
                String.format("Fullscreen ad queue has been updated for placement %s. One ad has been removed after onShow. Number of ads ready to show: %s", placementName, numberOfAdsReady == null ? 0 : numberOfAdsReady));
    }

    @Override
    public void onFullScreenAdQueueUpdated(String placementName, int numberOfAdsReady) {
        updateShowButtonState(placementName, numberOfAdsReady > 0);
    }
}
