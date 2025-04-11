package com.example.chartboost.mediation.sdk.demo.java;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chartboost.chartboostmediationsdk.ChartboostMediationSdk;
import com.chartboost.chartboostmediationsdk.ad.AdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationAdShowResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadRequest;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdView;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdViewListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadRequest;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadResult;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueue;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueListener;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueManager;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdShowListener;
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException;
import com.chartboost.chartboostmediationsdk.domain.Keywords;
import com.chartboost.core.ChartboostCore;
import com.chartboost.core.initialization.ModuleInitializationResult;
import com.chartboost.core.initialization.ModuleObserver;
import com.chartboost.core.initialization.SdkConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AdController {

    private final Map<String, ChartboostMediationFullscreenAd> fullscreenAds = new HashMap<>();
    private final String TAG = "AdController";
    private final Map<String, ChartboostMediationFullscreenAdQueue> adQueues = new HashMap<>();
    private boolean shouldUseQueue = false;

    public void initializeChartboost(
            String appId,
            OnLogStateChangeListener onLogStateChangeListener,
            Context context,
            MainActivity.OnInitializationCompleteListener onInitializationCompleteListener
    ) {
        onLogStateChangeListener.logState("Start initializing mediation");
        ChartboostCore.initializeSdkFromJava(context, new SdkConfiguration(appId, new ArrayList<>(), new HashSet<>()), new ModuleObserver() {
            @Override
            public void onModuleInitializationCompleted(@NonNull ModuleInitializationResult moduleInitializationResult) {
                if (moduleInitializationResult.getModuleId().equals(ChartboostMediationSdk.CORE_MODULE_ID)) {
                    if (moduleInitializationResult.getException() == null) {
                        onLogStateChangeListener.logState("Initialization finished");
                        ChartboostMediationSdk.setTestMode(context, true);
                        onInitializationCompleteListener.onInitializationCompleted();
                    } else {
                        onLogStateChangeListener.logState("Initialization exception %s");
                        onLogStateChangeListener.logState(moduleInitializationResult.getException().getMessage());
                    }
                }
            }
        });
    }

    public void loadBanner(String placementName, OnLogStateChangeListener onLogStateChangeListener, FrameLayout bannerLayout, Context context) {
        onLogStateChangeListener.logState("Start loading banner.");
        ChartboostMediationBannerAdView banner = new ChartboostMediationBannerAdView(
                context,
                placementName,
                ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD,
                createBannerAdListener(onLogStateChangeListener)
        );

        bannerLayout.addView(banner);
        banner.loadFromJava(
                new ChartboostMediationBannerAdLoadRequest(placementName, new Keywords(), ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD),
                new ChartboostMediationBannerAdLoadListener() {
                    @Override
                    public void onAdLoaded(@NonNull ChartboostMediationBannerAdLoadResult chartboostMediationBannerAdLoadResult) {
                        onLogStateChangeListener.logState("Banner loaded");
                    }
                });
    }

    public void loadFullscreenAd(
            String placementName,
            Context context,
            Button correspondingShowButton,
            OnLogStateChangeListener onLogStateChangeListener
    ) {
        ChartboostMediationFullscreenAdQueue queue = createAdQueue(placementName, correspondingShowButton, context, onLogStateChangeListener);
        if (shouldUseQueue && !queue.isRunning()) {
            queue.start();
            onLogStateChangeListener.logState(String.format("FullscreenAdQueue for %s started", placementName));
        } else if (!shouldUseQueue && queue.isRunning()) {
            queue.stop();
            onLogStateChangeListener.logState(String.format("FullscreenAdQueue for %s stopped", placementName));
        }
        onLogStateChangeListener.logState("Loading ad");
        ChartboostMediationFullscreenAdLoadRequest request = new ChartboostMediationFullscreenAdLoadRequest(placementName, new Keywords(), new HashMap<>());
        ChartboostMediationFullscreenAd.loadFullscreenAdFromJava(
                context,
                request,
                createFullscreenAdListener(placementName, correspondingShowButton, onLogStateChangeListener),
                createFullscreenAdLoadListener(placementName, correspondingShowButton, onLogStateChangeListener)
        );
    }

    public void showFullscreenAd(
            String placementName,
            Button correspondingShowButton,
            Activity activity,
            OnLogStateChangeListener onLogStateChangeListener
    ) {
        ChartboostMediationFullscreenAd ad = fullscreenAds.get(placementName);
        if (shouldUseQueue) {
            ChartboostMediationFullscreenAdQueue queue = getAdQueue(placementName);
            if (queue.hasNextAd()) {
                onLogStateChangeListener.logState("Using queue to show the ad");
                ad = queue.getNextAd();
                if (ad != null) {
                    // Be mindful that ads will need to be reattached to your ChartboostMediationFullscreenAdListener if you are listening to ad cycle events.
                    ad.setListener(createFullscreenAdListener(placementName, correspondingShowButton, onLogStateChangeListener));
                }
                fullscreenAds.put(placementName, ad);
            }
        }
        onLogStateChangeListener.logState("Fullscreen ad is about to show");
        if (ad != null) {
            ad.showFullscreenAdFromJava(activity, createFullscreenAdShowListener(correspondingShowButton, onLogStateChangeListener));
        } else {
            onLogStateChangeListener.logState("Fullscreen ad is null. Load an ad first.");
            correspondingShowButton.setEnabled(false);
        }
    }

    public ChartboostMediationFullscreenAdQueue getAdQueue(String placement) {
        return adQueues.get(placement);
    }

    public ChartboostMediationFullscreenAdQueue createAdQueue(
            String placement,
            Button correspondingShowButton,
            Context context,
            OnLogStateChangeListener onLogStateChangeListener
    ) {
        ChartboostMediationFullscreenAdQueue queue = ChartboostMediationFullscreenAdQueueManager.queue(context, placement);
        queue.setQueueCapacity(46);// this will update queue capacity only to the maximum capacity received on initialization
        queue.setAdQueueListener(createFullscreenAdQueueListener(correspondingShowButton, onLogStateChangeListener));
        adQueues.put(placement, queue);
        return queue;
    }

    public void setShouldUseQueue(boolean shouldUseQueue) {
        this.shouldUseQueue = shouldUseQueue;
    }

    private ChartboostMediationFullscreenAdLoadListener createFullscreenAdLoadListener(
            String placementName,
            Button button,
            OnLogStateChangeListener onLogStateChangeListener
    ) {
        return new ChartboostMediationFullscreenAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull ChartboostMediationFullscreenAdLoadResult chartboostMediationFullscreenAdLoadResult) {
                onLogStateChangeListener.logState("Ad loaded");
                fullscreenAds.put(placementName, chartboostMediationFullscreenAdLoadResult.getAd());
                button.setEnabled(fullscreenAds.get(placementName) != null);
            }
        };
    }

    private ChartboostMediationFullscreenAdListener createFullscreenAdListener(
            String placementName,
            Button showButton,
            OnLogStateChangeListener onLogStateChangeListener
    ) {
        return new ChartboostMediationFullscreenAdListener() {

            @Override
            public void onAdClicked(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                onLogStateChangeListener.logState("Ad clicked");
            }

            @Override
            public void onAdClosed(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, @Nullable ChartboostMediationAdException e) {
                onLogStateChangeListener.logState("Ad closed");
                ChartboostMediationFullscreenAdQueue queue = getAdQueue(placementName);
                if (shouldUseQueue) {
                    showButton.setEnabled(queue.hasNextAd());
                } else {
                    showButton.setEnabled(false);
                }
            }

            @Override
            public void onAdRewarded(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                onLogStateChangeListener.logState("Ad rewarded");
            }

            @Override
            public void onAdImpressionRecorded(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                onLogStateChangeListener.logState("Impression recorded");
            }

            @Override
            public void onAdExpired(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                onLogStateChangeListener.logState("Ad expired");
            }
        };
    }

    // To listen to ad queue events, you can use the ChartboostMediationFullscreenAdQueueListener
    private ChartboostMediationFullscreenAdQueueListener createFullscreenAdQueueListener(
            Button showButton,
            OnLogStateChangeListener onLogStateChangeListener
    ) {
        return new ChartboostMediationFullscreenAdQueueListener() {

            @Override
            public void onFullScreenAdQueueUpdated(@NonNull ChartboostMediationFullscreenAdQueue adQueue, @NonNull AdLoadResult result, int numberOfAdsReady) {
                onLogStateChangeListener.logState("Fullscreen ad queue has been updated for placement " + "placementName" + ". Number of ads ready to show: " + numberOfAdsReady);
                showButton.setEnabled(numberOfAdsReady > 0);
            }

            @Override
            public void onFullscreenAdQueueExpiredAdRemoved(@NonNull ChartboostMediationFullscreenAdQueue adQueue, int numberOfAdsReady) {
                onLogStateChangeListener.logState("Fullscreen ad queue expired ad has been removed for placement " + "placementName" + ". Number of ads ready to show: " + numberOfAdsReady);
                showButton.setEnabled(numberOfAdsReady > 0);
            }
        };
    }

    private ChartboostMediationFullscreenAdShowListener createFullscreenAdShowListener(
            Button correspondingShowButton,
            OnLogStateChangeListener onLogStateChangeListener
    ) {
        return new ChartboostMediationFullscreenAdShowListener() {
            @Override
            public void onAdShown(@NonNull ChartboostMediationAdShowResult chartboostMediationAdShowResult) {
                if (chartboostMediationAdShowResult.getError() != null) {
                    onLogStateChangeListener.logState(String.format("Fullscreen ad failed to show with error: %s", chartboostMediationAdShowResult.getError().getCause()));
                    correspondingShowButton.setEnabled(false);
                } else {
                    onLogStateChangeListener.logState("Fullscreen ad shown");
                }
            }
        };
    }

    private ChartboostMediationBannerAdViewListener createBannerAdListener(OnLogStateChangeListener onLogStateChangeListener) {
        return new ChartboostMediationBannerAdViewListener() {
            @Override
            public void onAdClicked(@NonNull String s) {
                onLogStateChangeListener.logState("Banner clicked");
            }

            @Override
            public void onAdImpressionRecorded(@NonNull String s) {
                onLogStateChangeListener.logState("Banner impression recorded");
            }

            @Override
            public void onAdViewAdded(@NonNull String s, @Nullable View view) {
                onLogStateChangeListener.logState("Ad view added");
            }
        };
    }
}
