package com.example.chartboost.mediation.sdk.demo.java.queuedads;

public class QueuedAdsUIState {

    // Interstitial States
    public final QueueControlState interstitialQueueControlState;
    public final boolean isShowInterstitialButtonEnabled;

    // Rewarded States
    public final QueueControlState rewardedQueueControlState;
    public final boolean isShowRewardedButtonEnabled;

    // Default initial state
    public static QueuedAdsUIState initialState() {
        return new QueuedAdsUIState(
                QueueControlState.START,
                false,
                QueueControlState.START,
                false
        );
    }

    private QueuedAdsUIState(QueueControlState interstitialQueueControlState,
                             boolean isShowInterstitialButtonEnabled,
                             QueueControlState rewardedQueueControlState,
                             boolean isShowRewardedButtonEnabled) {
        this.interstitialQueueControlState = interstitialQueueControlState;
        this.isShowInterstitialButtonEnabled = isShowInterstitialButtonEnabled;
        this.rewardedQueueControlState = rewardedQueueControlState;
        this.isShowRewardedButtonEnabled = isShowRewardedButtonEnabled;
    }

    public QueuedAdsUIState withInterstitialQueueControlState(QueueControlState state) {
        return new QueuedAdsUIState(state, this.isShowInterstitialButtonEnabled,
                this.rewardedQueueControlState,
                this.isShowRewardedButtonEnabled);
    }

    public QueuedAdsUIState withIsShowInterstitialButtonEnabled(boolean enabled) {
        return new QueuedAdsUIState(this.interstitialQueueControlState, enabled,
                this.rewardedQueueControlState,
                this.isShowRewardedButtonEnabled);
    }

    public QueuedAdsUIState withRewardedQueueControlState(QueueControlState state) {
        return new QueuedAdsUIState(this.interstitialQueueControlState, this.isShowInterstitialButtonEnabled,
                state,
                this.isShowRewardedButtonEnabled);
    }

    public QueuedAdsUIState withIsShowRewardedButtonEnabled(boolean enabled) {
        return new QueuedAdsUIState(this.interstitialQueueControlState, this.isShowInterstitialButtonEnabled,
                this.rewardedQueueControlState,
                enabled);
    }
}
