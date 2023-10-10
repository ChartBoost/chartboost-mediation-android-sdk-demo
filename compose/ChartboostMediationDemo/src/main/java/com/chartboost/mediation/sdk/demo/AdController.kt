package com.chartboost.mediation.sdk.demo

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.chartboost.heliumsdk.HeliumInitializationOptions
import com.chartboost.heliumsdk.HeliumSdk
import com.chartboost.heliumsdk.PartnerInitializationResultsData
import com.chartboost.heliumsdk.PartnerInitializationResultsObserver
import com.chartboost.heliumsdk.ad.ChartboostMediationAdLoadRequest
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAd
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdListener
import com.chartboost.heliumsdk.ad.HeliumBannerAd
import com.chartboost.heliumsdk.ad.HeliumBannerAdListener
import com.chartboost.heliumsdk.ad.HeliumFullscreenAdListener
import com.chartboost.heliumsdk.ad.HeliumInterstitialAd
import com.chartboost.heliumsdk.ad.HeliumRewardedAd
import com.chartboost.heliumsdk.domain.ChartboostMediationAdException
import com.chartboost.heliumsdk.domain.Keywords
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
        interstitialAd?.destroy()
        rewardedAd?.destroy()
    }

    /**
     * The Chartboost Mediation fullscreen ad. This is used for all fullscreen ad types and is the
     * recommended way to load and show fullscreen ads.
     */
    private var fullscreenAd: ChartboostMediationFullscreenAd? = null

    /**
     * The Chartboost Mediation banner ad.
     */
    private var bannerAd: HeliumBannerAd? = null

    /**
     * The Chartboost Mediation interstitial ad (deprecated). This is only used here for demo purposes.
     * Use the [ChartboostMediationFullscreenAd] instead.
     */
    private var interstitialAd: HeliumInterstitialAd? = null

    /**
     * The Chartboost Mediation rewarded ad (deprecated). This is only used here for demo purposes.
     * Use the [ChartboostMediationFullscreenAd] instead.
     */
    private var rewardedAd: HeliumRewardedAd? = null

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
     * @param appSignature The Chartboost Mediation app signature.
     * @param logState The log state to update.
     *
     * @return A result indicating whether the SDK was initialized successfully.
     */
    suspend fun initialize(
        context: Context,
        appId: String,
        appSignature: String,
        logState: MutableList<String>,
    ): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            HeliumSdk.start(
                context,
                appId,
                appSignature,
                HeliumInitializationOptions(),
                object : HeliumSdk.HeliumSdkListener {
                    override fun didInitialize(error: Error?) {
                        handleInitializationResults(error, continuation, logState)
                    }
                }
            )

            HeliumSdk.setDebugMode(true)
        }
    }

    /**
     * Loads an ad.
     *
     * @param context The context.
     * @param adType The type of ad to load.
     * @param placementName The name of the placement to load.
     * @param logState The log state to update.
     * @param shouldUseFullscreenApi True if the fullscreen API should be used, false otherwise.
     * @param showBtnEnabled True if the corresponding show button should be enabled, false otherwise.
     */
    fun loadAd(
        context: Context,
        adType: AdType,
        placementName: String,
        logState: MutableList<String>,
        shouldUseFullscreenApi: MutableState<Boolean>,
        showBtnEnabled: MutableState<Boolean>,
    ) {
        when (adType) {
            AdType.BANNER -> loadBannerAd(
                context,
                placementName,
                HeliumBannerAd.HeliumBannerSize.STANDARD,
                logState
            )

            AdType.INTERSTITIAL, AdType.REWARDED_VIDEO, AdType.REWARDED_INTERSTITIAL -> funnelFullscreenAdFlavor(
                adType,
                shouldUseFullscreenApi.value,
                context,
                placementName,
                logState,
                showBtnEnabled
            )
        }
    }

    /**
     * Shows an ad.
     *
     * @param context The context.
     * @param adType The type of ad to show.
     * @param logState The log state to update.
     * @param shouldUseFullscreenApi True if the fullscreen API should be used, false otherwise.
     */
    fun showAd(
        context: Context,
        adType: AdType,
        logState: MutableList<String>,
        shouldUseFullscreenApi: MutableState<Boolean>,
    ) {
        when (adType) {
            AdType.BANNER -> {
                // NO-OP. The banner ad is shown automatically when it is loaded.
            }

            AdType.INTERSTITIAL ->
                if (shouldUseFullscreenApi.value) showFullscreenAd(
                    context,
                    logState
                ) else showInterstitialAd(
                    logState
                )

            AdType.REWARDED_VIDEO ->
                if (shouldUseFullscreenApi.value) showFullscreenAd(
                    context,
                    logState
                ) else showRewardedAd(
                    logState
                )

            AdType.REWARDED_INTERSTITIAL -> showFullscreenAd(context, logState)
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
        size: HeliumBannerAd.HeliumBannerSize,
        logState: MutableList<String>,
    ): HeliumBannerAd {
        logState.add("Banner ad is about to load")

        return HeliumBannerAd(context, placementName, size, object : HeliumBannerAdListener {
            override fun onAdCached(
                placementName: String,
                loadId: String,
                winningBidInfo: Map<String, String>,
                error: ChartboostMediationAdException?,
            ) {
                logState.add("Banner ad loaded ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}")
            }

            override fun onAdClicked(placementName: String) {
                logState.add("Banner ad clicked")
            }

            override fun onAdImpressionRecorded(placementName: String) {
                logState.add("Banner ad impression recorded")
            }
        })
    }

    /**
     * Handles the initialization results.
     *
     * @param error The error, if any.
     * @param continuation The continuation to resume.
     * @param logState The log state to update.
     */
    private fun handleInitializationResults(
        error: Error?,
        continuation: Continuation<Result<Unit>>,
        logState: MutableList<String>,
    ) {
        // [Optional] Subscribe to partner initialization results to get the initialization metrics data.
        HeliumSdk.subscribeInitializationResults(object : PartnerInitializationResultsObserver {
            override fun onPartnerInitializationResultsReady(data: PartnerInitializationResultsData) {
                logState.add("Partner initialization metrics data is available: ${data.data}")
            }
        })

        logState.add("Chartboost Mediation SDK initialization ${if (error != null) "failed with error: ${error.cause}" else "succeeded"}")
        continuation.resume(if (error != null) Result.failure(error) else Result.success(Unit))
    }

    /**
     * Funnel the fullscreen ad flavor to the appropriate loading logic.
     *
     * @param adType The type of ad to load.
     * @param useFullscreen True if the fullscreen API should be used, false otherwise.
     * @param context The context.
     * @param placementName The name of the placement to load.
     * @param logState The log state to update.
     * @param showBtnEnabled True if the corresponding show button should be enabled, false otherwise.
     */
    private fun funnelFullscreenAdFlavor(
        adType: AdType,
        useFullscreen: Boolean,
        context: Context,
        placementName: String,
        logState: MutableList<String>,
        showBtnEnabled: MutableState<Boolean>,
    ) {
        val method = if (useFullscreen) ::loadFullscreenAd else when (adType) {
            AdType.INTERSTITIAL -> ::loadInterstitialAd
            AdType.REWARDED_VIDEO, AdType.REWARDED_INTERSTITIAL -> ::loadRewardedAd
            else -> {
                logState.add("Failed to load fullscreen ad. Ad type '$adType' is not supported")
                return
            }
        }

        method(context, placementName, logState, showBtnEnabled)
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
        showBtnEnabled: MutableState<Boolean>,
    ) {
        CoroutineScope(Main).launch {
            logState.add("Fullscreen ad is about to load")

            val request = ChartboostMediationAdLoadRequest(placementName, Keywords())
            val result = HeliumSdk.loadFullscreenAd(
                context,
                request,
                object : ChartboostMediationFullscreenAdListener {
                    override fun onAdClicked(ad: ChartboostMediationFullscreenAd) {
                        logState.add("Fullscreen ad clicked")
                    }

                    override fun onAdClosed(
                        ad: ChartboostMediationFullscreenAd,
                        error: ChartboostMediationAdException?,
                    ) {
                        logState.add("Fullscreen ad closed ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}")
                        showBtnEnabled.value = false
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
                })

            if (result.error != null) {
                logState.add("Fullscreen ad failed to load with error: ${result.error?.cause}")
            } else {
                fullscreenAd = result.ad
                logState.add("Fullscreen ad loaded successfully")
            }

            showBtnEnabled.value = result.error == null
        }
    }

    /**
     * Loads an interstitial ad.
     *
     * @param context The context.
     * @param placementName The name of the placement to load.
     * @param logState The log state to update.
     * @param showBtnEnabled True if the corresponding show button should be enabled, false otherwise.
     */
    private fun loadInterstitialAd(
        context: Context,
        placementName: String,
        logState: MutableList<String>,
        showBtnEnabled: MutableState<Boolean>,
    ) {
        logState.add("Interstitial ad is about to load")

        interstitialAd = HeliumInterstitialAd(
            context,
            placementName,
            object : HeliumFullscreenAdListener {
                override fun onAdCached(
                    placementName: String,
                    loadId: String,
                    winningBidInfo: Map<String, String>,
                    error: ChartboostMediationAdException?,
                ) {
                    logState.add("Interstitial ad loaded ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}")
                    showBtnEnabled.value = error == null
                }

                override fun onAdClicked(placementName: String) {
                    logState.add("Interstitial ad clicked")
                }

                override fun onAdClosed(
                    placementName: String,
                    error: ChartboostMediationAdException?,
                ) {
                    logState.add("Interstitial ad closed ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}")
                    showBtnEnabled.value = false
                }

                override fun onAdImpressionRecorded(placementName: String) {
                    logState.add("Interstitial ad impression recorded")
                }

                override fun onAdRewarded(placementName: String) {
                    logState.add("Interstitial ad rewarded")
                }

                override fun onAdShown(
                    placementName: String,
                    error: ChartboostMediationAdException?,
                ) {
                    logState.add("Interstitial ad shown ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}")
                }
            })

        interstitialAd?.load() ?: run {
            logState.add("Interstitial ad failed to load. Ad is null")
            showBtnEnabled.value = false
        }
    }

    /**
     * Loads a rewarded ad.
     *
     * @param context The context.
     * @param placementName The name of the placement to load.
     * @param logState The log state to update.
     * @param showBtnEnabled True if the corresponding show button should be enabled, false otherwise.
     */
    private fun loadRewardedAd(
        context: Context,
        placementName: String,
        logState: MutableList<String>,
        showBtnEnabled: MutableState<Boolean>,
    ) {
        logState.add("Rewarded ad is about to load")

        rewardedAd = HeliumRewardedAd(
            context,
            placementName,
            object : HeliumFullscreenAdListener {
                override fun onAdCached(
                    placementName: String,
                    loadId: String,
                    winningBidInfo: Map<String, String>,
                    error: ChartboostMediationAdException?,
                ) {
                    logState.add("Rewarded ad loaded ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}")
                    showBtnEnabled.value = error == null
                }

                override fun onAdClicked(placementName: String) {
                    logState.add("Rewarded ad clicked")
                }

                override fun onAdClosed(
                    placementName: String,
                    error: ChartboostMediationAdException?,
                ) {
                    logState.add("Rewarded ad closed ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}")
                    showBtnEnabled.value = false
                }

                override fun onAdImpressionRecorded(placementName: String) {
                    logState.add("Rewarded ad impression recorded")
                }

                override fun onAdRewarded(placementName: String) {
                    logState.add("Rewarded ad rewarded")
                }

                override fun onAdShown(
                    placementName: String,
                    error: ChartboostMediationAdException?,
                ) {
                    logState.add("Rewarded ad shown ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}")
                }
            }
        )

        rewardedAd?.load() ?: run {
            logState.add("Rewarded ad failed to load. Ad is null")
            showBtnEnabled.value = false
        }
    }

    /**
     * Shows a fullscreen ad.
     *
     * @param context The context.
     * @param logState The log state to update.
     */
    private fun showFullscreenAd(context: Context, logState: MutableList<String>) {
        CoroutineScope(Main).launch {
            logState.add("Fullscreen ad is about to show")
            val result = fullscreenAd?.show(context)

            if (result?.error != null) {
                logState.add("Fullscreen ad failed to show with error: ${result.error?.cause}")
            } else {
                logState.add("Fullscreen ad shown successfully")
            }
        }
    }

    /**
     * Shows an interstitial ad.
     *
     * @param logState The log state to update.
     */
    private fun showInterstitialAd(logState: MutableList<String>) {
        logState.add("Interstitial ad is about to show")
        interstitialAd?.show() ?: logState.add("Interstitial ad failed to show. Ad is null")
    }

    /**
     * Shows a rewarded ad.
     *
     * @param logState The log state to update.
     */
    private fun showRewardedAd(logState: MutableList<String>) {
        logState.add("Rewarded ad is about to show")
        rewardedAd?.show() ?: logState.add("Rewarded ad failed to show. Ad is null")
    }
}
