package com.chartboost.mediation.sdk.demo

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.ui.input.key.Key
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.chartboost.chartboostmediationsdk.ChartboostMediationSdk
import com.chartboost.chartboostmediationsdk.PartnerAdapterInitializationResultsData
import com.chartboost.chartboostmediationsdk.PartnerAdapterInitializationResultsObserver
import com.chartboost.chartboostmediationsdk.ad.AdLoadResult
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationAdLoadRequest
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdView
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdViewListener
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdListener
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdLoadRequest
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueue
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueListener
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAdQueueManager
import com.chartboost.chartboostmediationsdk.domain.ChartboostMediationAdException
import com.chartboost.chartboostmediationsdk.domain.Keywords
import com.chartboost.core.ChartboostCore
import com.chartboost.core.initialization.ModuleInitializationResult
import com.chartboost.core.initialization.ModuleObserver
import com.chartboost.core.initialization.SdkConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

/**
 * The ad controller, responsible for initializing the Chartboost Mediation SDK and loading and showing ads.
 */
object AdController : DefaultLifecycleObserver {
    /**
     * Invalidates the ads when the lifecycle owner is destroyed.
     *
     * @param owner The lifecycle owner.
     */
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)

        fullscreenAd?.invalidate()
        bannerAd?.destroy()
    }

    /**
     * The Chartboost Mediation fullscreen ad. This is used for all fullscreen ad types and is the
     * recommended way to load and show fullscreen ads.
     */
    private var fullscreenAd: ChartboostMediationFullscreenAd? = null

    /**
     * The Chartboost Mediation banner ad.
     */
    private var bannerAd: ChartboostMediationBannerAdView? = null

    /**
     * An enumeration of the supported ad types.
     */
    enum class AdType {
        BANNER,
        INTERSTITIAL,
        REWARDED_VIDEO,
        REWARDED_INTERSTITIAL,
    }

    /**
     * Initializes the Chartboost Mediation SDK. This must be called before any other SDK methods.
     *
     * @param context The context.
     * @param appId The Chartboost Mediation app ID.
     * @param logState The log state to update.
     */
    suspend fun initialize(
        context: Context,
        appId: String,
        logState: MutableList<String>,
    ) {
        val configuration = SdkConfiguration(
            chartboostApplicationIdentifier = appId,
            modules = listOf()
        )
        ChartboostCore.initializeSdk(
            context,
            configuration,
            object : ModuleObserver {
                override fun onModuleInitializationCompleted(result: ModuleInitializationResult) {
                    if (result.moduleId == ChartboostMediationSdk.CORE_MODULE_ID) {
                        handleInitializationResults(result, logState)
                    }
                }
            },
        )

        ChartboostMediationSdk.setTestMode(
            context,
            true,
        )
    }

    /**
     * Loads an ad.
     *
     * @param context The context.
     * @param adType The type of ad to load.
     * @param placementName The name of the placement to load.
     * @param logState The log state to update.
     * @param shouldUseFullscreenAdQueue True if the full screen ad queue is enabled, false otherwise.
     * @param showBtnEnabled True if the corresponding show button should be enabled, false otherwise.
     */
    fun loadAd(
        context: Context,
        adType: AdType,
        placementName: String,
        logState: MutableList<String>,
        shouldUseFullscreenAdQueue: MutableState<Boolean>,
        showBtnEnabled: MutableState<Boolean>,
    ) {
        when (adType) {
            AdType.BANNER ->
                loadBannerAd(
                    context,
                    placementName,
                    ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD,
                    logState,
                )

            AdType.INTERSTITIAL, AdType.REWARDED_VIDEO, AdType.REWARDED_INTERSTITIAL ->
                loadFullscreenAd(
                    context,
                    placementName,
                    logState,
                    shouldUseFullscreenAdQueue.value,
                    showBtnEnabled,
                )
        }
    }

    /**
     * Shows an ad.
     *
     * @param context The context.
     * @param adType The type of ad to show.
     * @param placementName The placement name.
     * @param logState The log state to update.
     * @param shouldUseFullscreenAdQueue True if the full screen ad queue is enabled, false otherwise.
     * @param showBtnEnabled True if the corresponding show button should be enabled, false otherwise.
     */
    fun showAd(
        context: Context,
        adType: AdType,
        placementName: String,
        logState: MutableList<String>,
        shouldUseFullscreenAdQueue: MutableState<Boolean>,
        showBtnEnabled: MutableState<Boolean>,
    ) {
        when (adType) {
            AdType.BANNER -> {
                // NO-OP. The banner ad is shown automatically when it is loaded.
            }

            AdType.INTERSTITIAL, AdType.REWARDED_VIDEO, AdType.REWARDED_INTERSTITIAL ->
                showFullscreenAd(
                    context,
                    placementName,
                    shouldUseFullscreenAdQueue,
                    logState,
                    showBtnEnabled,
                )
        }
    }

    /**
     * Loads a banner ad.
     *
     * @param context The context.
     * @param placementName The name of the placement to load.
     * @param size The size of the banner ad.
     * @param logState The log state to update.
     */
    fun loadBannerAd(
        context: Context,
        placementName: String,
        size: ChartboostMediationBannerAdView.ChartboostMediationBannerSize,
        logState: MutableList<String>,
    ): ChartboostMediationBannerAdView {
        logState.add("Banner ad is about to load")

        return ChartboostMediationBannerAdView(
            context,
            placementName,
            size,
            object : ChartboostMediationBannerAdViewListener {
                override fun onAdClicked(placementName: String) {
                    logState.add("Banner ad clicked")
                }

                override fun onAdImpressionRecorded(placementName: String) {
                    logState.add("Banner ad impression recorded")
                }

                override fun onAdViewAdded(
                    placement: String,
                    child: View?,
                ) {
                    logState.add("Banner ad view added")
                }
            },
        )
    }

    /**
     * Handles the initialization results.
     *
     * @param result The module initialization result
     * @param logState The log state to update.
     */
    private fun handleInitializationResults(
        result: ModuleInitializationResult,
        logState: MutableList<String>,
    ) {
        // [Optional] Subscribe to partner initialization results to get the initialization metrics data.
        ChartboostMediationSdk.subscribePartnerAdapterInitializationResults(
            object : PartnerAdapterInitializationResultsObserver {
                override fun onPartnerAdapterInitializationResultsReady(data: PartnerAdapterInitializationResultsData) {
                    logState.add("Partner initialization metrics data is available: ${data.data}")
                }
            },
        )

        val exception = result.exception
        logState.add("Chartboost Mediation SDK initialization ${if (exception != null) "failed with error: ${exception.cause}" else "succeeded"}")
    }

    /**
     * Loads a fullscreen ad.
     *
     * @param context The context.
     * @param placementName The name of the placement to load.
     * @param logState The log state to update.
     * @param showBtnEnabled True if the corresponding show button should be enabled, false otherwise.
     */
    private fun loadFullscreenAd(
        context: Context,
        placementName: String,
        logState: MutableList<String>,
        isFullscreenAdQueue: Boolean,
        showBtnEnabled: MutableState<Boolean>,
    ) {
        CoroutineScope(Main).launch {
            if (isFullscreenAdQueue) {
                val queue = creteQueue(context, placementName, logState, showBtnEnabled)
                if (!queue.isRunning) {
                    queue.start()
                    logState.add("Fullscreen ad queue has stared.")
                } else {
                    queue.stop()
                    logState.add("Fullscreen ad queue has stopped.")
                }
            } else {
                logState.add("Fullscreen ad queue ")
                logState.add("Fullscreen ad is about to load")

                val request = ChartboostMediationFullscreenAdLoadRequest(placementName, Keywords())
                val result =
                    ChartboostMediationFullscreenAd.loadFullscreenAd(
                        context,
                        request,
                        createFullscreenAdListener(logState, false, showBtnEnabled),
                    )

                if (result.error != null) {
                    logState.add("Fullscreen ad failed to load with error: ${result.error?.cause}")
                } else {
                    fullscreenAd = result.ad
                    logState.add("Fullscreen ad loaded successfully")
                }

                showBtnEnabled.value = result.error == null
            }
        }
    }

    /**
     * Shows a fullscreen ad.
     *
     * @param context The context.
     * @param logState The log state to update.
     */
    private fun showFullscreenAd(
        context: Context,
        placementName: String,
        shouldUseFullscreenAdQueue: MutableState<Boolean>,
        logState: MutableList<String>,
        showBtnEnabled: MutableState<Boolean>,
    ) {
        CoroutineScope(Main).launch {
            if (shouldUseFullscreenAdQueue.value) {
                logState.add("Getting fullscreen ad from queue.")
                val queue = getQueue(context, placementName)
                if (queue.hasNextAd()) {
                    fullscreenAd =
                        queue.getNextAd()?.apply {
                            listener =
                                createFullscreenAdListener(
                                    logState,
                                    shouldUseFullscreenAdQueue.value,
                                    showBtnEnabled,
                                )
                        }
                } else {
                    logState.add("Could not retrieve ad from queue.")
                    showBtnEnabled.value = false
                }
            }

            logState.add("Fullscreen ad is about to show")
            val result = fullscreenAd?.show(context as Activity)

            if (result?.error != null) {
                logState.add("Fullscreen ad failed to show with error: ${result.error?.cause}")
            } else {
                logState.add("Fullscreen ad shown successfully")
            }
        }
    }

    private fun createFullscreenAdListener(
        logState: MutableList<String>,
        isFullscreenAdQueue: Boolean,
        showBtnEnabled: MutableState<Boolean>,
    ): ChartboostMediationFullscreenAdListener =
        object : ChartboostMediationFullscreenAdListener {
            override fun onAdClicked(ad: ChartboostMediationFullscreenAd) {
                logState.add("Fullscreen ad clicked")
            }

            override fun onAdClosed(
                ad: ChartboostMediationFullscreenAd,
                error: ChartboostMediationAdException?,
            ) {
                logState.add(
                    "Fullscreen ad closed ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}",
                )
                if (!isFullscreenAdQueue) showBtnEnabled.value = false
            }

            override fun onAdExpired(ad: ChartboostMediationFullscreenAd) {
                logState.add("Fullscreen ad expired")
            }

            override fun onAdImpressionRecorded(ad: ChartboostMediationFullscreenAd) {
                logState.add("Fullscreen ad impression recorded")
            }

            override fun onAdRewarded(ad: ChartboostMediationFullscreenAd) {
                logState.add("Fullscreen ad rewarded")
            }
        }

    /**
     * Get a queue.
     *
     * @param context [Context] The context.
     * @param placementName [String] The name of the placement.
     *
     * @return A [ChartboostMediationFullscreenAdQueue].
     */
    private fun getQueue(
        context: Context,
        placementName: String,
    ) = ChartboostMediationFullscreenAdQueueManager.queue(context, placementName)

    /**
     * Creates an ad queue and attach a [ChartboostMediationFullscreenAdQueueListener].
     *
     * @param context [Context] The context.
     * @param placementName [String] The name of the placement.
     *
     * @return A [ChartboostMediationFullscreenAdQueue].
     */
    private fun creteQueue(
        context: Context,
        placementName: String,
        logState: MutableList<String>,
        showBtnEnabled: MutableState<Boolean>,
    ) = ChartboostMediationFullscreenAdQueueManager.queue(context, placementName).apply {
        adQueueListener =
            object : ChartboostMediationFullscreenAdQueueListener {
                override fun onFullScreenAdQueueUpdated(
                    adQueue: ChartboostMediationFullscreenAdQueue,
                    result: AdLoadResult,
                    numberOfAdsReady: Int,
                ) {
                    logState.add(
                        "Fullscreen ad queue loaded ${
                            if (result.error != null) {
                                " with error: ${result.error?.cause}"
                            } else {
                                ""
                            }
                        }",
                    )

                    if (numberOfAdsReady > 0) {
                        logState.add("Fullscreen ad queue updated. Number of ads ready: $numberOfAdsReady")
                        showBtnEnabled.value = true
                    }
                }

                override fun onFullscreenAdQueueExpiredAdRemoved(
                    adQueue: ChartboostMediationFullscreenAdQueue,
                    numberOfAdsReady: Int,
                ) {
                    logState.add("Fullscreen ad queue removed an expired ad. Number of ads ready: $numberOfAdsReady")
                }
            }
    }
}
