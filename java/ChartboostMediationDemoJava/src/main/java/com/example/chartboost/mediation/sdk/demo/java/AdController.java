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
import java.util.concurrent.ConcurrentHashMap;

public class AdController {

    private static final int UNCONFIGURED_AD_QUEUE_CAPACITY = 5;
    private final Map<String, ChartboostMediationFullscreenAd> fullscreenAds = new ConcurrentHashMap();
    private final Map<String, ChartboostMediationFullscreenAdQueue> adQueues = new ConcurrentHashMap();
    private boolean shouldUseQueue = false;

    final public void initializeChartboost(
            String appId,
            UILogsListener uiLogsListener,
            Context context,
            OnInitializationCompleteListener onInitializationCompleteListener
    ) {
        uiLogsListener.add("Start initializing mediation");
        ChartboostCore.initializeSdkFromJava(context, new SdkConfiguration(appId, new ArrayList<>(), new HashSet<>()), new ModuleObserver() {
            @Override
            public void onModuleInitializationCompleted(@NonNull ModuleInitializationResult moduleInitializationResult) {
                if (moduleInitializationResult.getModuleId().equals(ChartboostMediationSdk.CORE_MODULE_ID)) {
                    if (moduleInitializationResult.getException() == null) {
                        uiLogsListener.add("Initialization finished");
                        ChartboostMediationSdk.setTestMode(context, true);
                        onInitializationCompleteListener.onInitializationCompleted();
                    } else {
                        uiLogsListener.add(String.format("Initialization exception %s", moduleInitializationResult.getException()));
                        onInitializationCompleteListener.onInitializationException();
                    }
                }
            }
        });
    }

    final public void loadBanner(String placementName, UILogsListener uiLogsListener, FrameLayout bannerLayout, Context context) {
        uiLogsListener.add("Start loading banner.");
        ChartboostMediationBannerAdView banner = new ChartboostMediationBannerAdView(
                context,
                placementName,
                ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD,
                createBannerAdListener(uiLogsListener)
        );

        bannerLayout.removeAllViews();
        bannerLayout.addView(banner);
        banner.loadFromJava(
                new ChartboostMediationBannerAdLoadRequest(placementName, new Keywords(), ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD),
                new ChartboostMediationBannerAdLoadListener() {
                    @Override
                    public void onAdLoaded(@NonNull ChartboostMediationBannerAdLoadResult chartboostMediationBannerAdLoadResult) {
                        if (chartboostMediationBannerAdLoadResult.getError() == null) {
                            uiLogsListener.add("Banner loaded");
                        } else {
                            uiLogsListener.add(String.format("Error loading ad, code %s : ", chartboostMediationBannerAdLoadResult.getError().getCode(), chartboostMediationBannerAdLoadResult.getError().getMessage()));

                        }
                    }
                });
    }

    final public void loadFullscreenAd(
            String placementName,
            Context context,
            Button loadButton,
            Button correspondingShowButton,
            UILogsListener uiLogsListener
    ) {
        uiLogsListener.add("Loading ad");
        ChartboostMediationFullscreenAdLoadRequest request = new ChartboostMediationFullscreenAdLoadRequest(placementName, new Keywords(), new HashMap<>());
        ChartboostMediationFullscreenAd.loadFullscreenAdFromJava(
                context,
                request,
                createFullscreenAdListener(placementName, correspondingShowButton, uiLogsListener),
                createFullscreenAdLoadListener(placementName, loadButton, correspondingShowButton, uiLogsListener)
        );
    }

    final public void showFullscreenAd(
            String placementName,
            Button correspondingShowButton,
            Activity activity,
            UILogsListener uiLogsListener
    ) {
        ChartboostMediationFullscreenAd ad = fullscreenAds.get(placementName);
        if (shouldUseQueue) {
            ChartboostMediationFullscreenAdQueue queue = getAdQueue(placementName);
            if (queue.hasNextAd()) {
                uiLogsListener.add("Using queue to show the ad");
                ad = queue.getNextAd();
                if (ad != null) {
                    // Be mindful that ads will need to be reattached to your ChartboostMediationFullscreenAdListener if you are listening to ad cycle events.
                    ad.setListener(createFullscreenAdListener(placementName, correspondingShowButton, uiLogsListener));
                } else {
                    uiLogsListener.add("Ad retrieved from the queue is null");
                }
                fullscreenAds.put(placementName, ad);
            } else {
                uiLogsListener.add("No ads in the queue");
            }
        }
        uiLogsListener.add("Fullscreen ad is about to show");
        if (ad != null) {
            ad.showFullscreenAdFromJava(activity, createFullscreenAdShowListener(correspondingShowButton, uiLogsListener));
        } else {
            uiLogsListener.add("Fullscreen ad is null. Load an ad first.");
            correspondingShowButton.setEnabled(false);
        }
    }

    final public ChartboostMediationFullscreenAdQueue getAdQueue(String placement) {
        return adQueues.get(placement);
    }

    final public void createAdQueue(
            String placement,
            Button correspondingShowButton,
            Context context,
            UILogsListener uiLogsListener
    ) {
        ChartboostMediationFullscreenAdQueue queue = ChartboostMediationFullscreenAdQueueManager.queue(context, placement);
        queue.setQueueCapacity(UNCONFIGURED_AD_QUEUE_CAPACITY);// this will update queue capacity only to the maximum capacity received on initialization
        queue.setAdQueueListener(createFullscreenAdQueueListener(placement, correspondingShowButton, uiLogsListener));
        adQueues.put(placement, queue);
    }

    final public void handleQueueToggle(boolean shouldUseQueue) {
        this.shouldUseQueue = shouldUseQueue;
        for (ChartboostMediationFullscreenAdQueue queue : adQueues.values()) {
            if (shouldUseQueue) {
                queue.start();
            } else {
                queue.stop();
            }
        }
    }

    private ChartboostMediationFullscreenAdLoadListener createFullscreenAdLoadListener(
            String placementName,
            Button loadButton,
            Button correspondingShowButton,
            UILogsListener uiLogsListener
    ) {
        return new ChartboostMediationFullscreenAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull ChartboostMediationFullscreenAdLoadResult chartboostMediationFullscreenAdLoadResult) {
                if (chartboostMediationFullscreenAdLoadResult.getError() == null) {
                    uiLogsListener.add("Ad loaded");
                    fullscreenAds.put(placementName, chartboostMediationFullscreenAdLoadResult.getAd());
                    correspondingShowButton.setEnabled(fullscreenAds.get(placementName) != null);
                } else {
                    uiLogsListener.add(String.format("Error loading ad, code: %s : %s", chartboostMediationFullscreenAdLoadResult.getError().getCode(), chartboostMediationFullscreenAdLoadResult.getError().getMessage()));
                }
                loadButton.setEnabled(true);
            }
        };
    }

    private ChartboostMediationFullscreenAdListener createFullscreenAdListener(
            String placementName,
            Button showButton,
            UILogsListener uiLogsListener
    ) {
        return new ChartboostMediationFullscreenAdListener() {

            @Override
            public void onAdClicked(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                uiLogsListener.add("Ad clicked");
            }

            @Override
            public void onAdClosed(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd, @Nullable ChartboostMediationAdException e) {
                uiLogsListener.add("Ad closed");
                ChartboostMediationFullscreenAdQueue queue = getAdQueue(placementName);
                if (shouldUseQueue) {
                    showButton.setEnabled(queue.hasNextAd());
                } else {
                    showButton.setEnabled(false);
                }
            }

            @Override
            public void onAdRewarded(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                uiLogsListener.add("Ad rewarded");
            }

            @Override
            public void onAdImpressionRecorded(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                uiLogsListener.add("Impression recorded");
            }

            @Override
            public void onAdExpired(@NonNull ChartboostMediationFullscreenAd chartboostMediationFullscreenAd) {
                uiLogsListener.add("Ad expired");
            }
        };
    }

    // To listen to ad queue events, you can use the ChartboostMediationFullscreenAdQueueListener
    private ChartboostMediationFullscreenAdQueueListener createFullscreenAdQueueListener(
            String placementName, Button showButton,
            UILogsListener uiLogsListener) {
        return new ChartboostMediationFullscreenAdQueueListener() {

            @Override
            public void onFullScreenAdQueueUpdated(@NonNull ChartboostMediationFullscreenAdQueue adQueue, @NonNull AdLoadResult result, int numberOfAdsReady) {
                uiLogsListener.add("Fullscreen ad queue has been updated for placement " + placementName + ". Number of ads ready to show: " + numberOfAdsReady);
                showButton.setEnabled(numberOfAdsReady > 0);
            }

            @Override
            public void onFullscreenAdQueueExpiredAdRemoved(@NonNull ChartboostMediationFullscreenAdQueue adQueue, int numberOfAdsReady) {
                uiLogsListener.add("Fullscreen ad queue expired ad has been removed for placement " + placementName + ". Number of ads ready to show: " + numberOfAdsReady);
                showButton.setEnabled(numberOfAdsReady > 0);
            }
        };
    }

    private ChartboostMediationFullscreenAdShowListener createFullscreenAdShowListener(
            Button correspondingShowButton,
            UILogsListener uiLogsListener
    ) {
        return new ChartboostMediationFullscreenAdShowListener() {
            @Override
            public void onAdShown(@NonNull ChartboostMediationAdShowResult chartboostMediationAdShowResult) {
                if (chartboostMediationAdShowResult.getError() != null) {
                    uiLogsListener.add(String.format("Fullscreen ad failed to show with error: %s", chartboostMediationAdShowResult.getError().getCause()));
                    correspondingShowButton.setEnabled(false);
                } else {
                    uiLogsListener.add("Fullscreen ad shown");
                }
            }
        };
    }

    private ChartboostMediationBannerAdViewListener createBannerAdListener(UILogsListener uiLogsListener) {
        return new ChartboostMediationBannerAdViewListener() {
            @Override
            public void onAdClicked(@NonNull String s) {
                uiLogsListener.add("Banner clicked");
            }

            @Override
            public void onAdImpressionRecorded(@NonNull String s) {
                uiLogsListener.add("Banner impression recorded");
            }

            @Override
            public void onAdViewAdded(@NonNull String s, @Nullable View view) {
                uiLogsListener.add("Ad view added");
            }
        };
    }

    final public void clear() {
        adQueues.clear();
        fullscreenAds.clear();
    }
}
