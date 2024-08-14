package com.chartboost.mediation.sdk.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chartboost.heliumsdk.HeliumSdk
import com.chartboost.heliumsdk.ad.AdLoadResult
import com.chartboost.heliumsdk.ad.ChartboostMediationAdLoadRequest
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAd
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdListener
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdLoadResult
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdQueue
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdQueueListener
import com.chartboost.heliumsdk.ad.ChartboostMediationFullscreenAdQueueManager
import com.chartboost.heliumsdk.ad.HeliumBannerAd
import com.chartboost.heliumsdk.ad.HeliumBannerAdListener
import com.chartboost.heliumsdk.ad.HeliumFullscreenAdListener
import com.chartboost.heliumsdk.ad.HeliumInterstitialAd
import com.chartboost.heliumsdk.ad.HeliumRewardedAd
import com.chartboost.heliumsdk.domain.AdFormat
import com.chartboost.heliumsdk.domain.ChartboostMediationAdException
import com.chartboost.heliumsdk.domain.Keywords
import com.chartboost.mediation.sdk.demo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var _bannerAd: HeliumBannerAd? = null
    private lateinit var binding: ActivityMainBinding
    private var _fullscreenAd: ChartboostMediationFullscreenAdLoadResult? = null
    private var _rewardedAd: HeliumRewardedAd? = null
    private var _interstitialAd: HeliumInterstitialAd? = null

    // Chartboost Mediation Fullscreen Ad Queues
    private var fullscreenInterstitialAdQueue: ChartboostMediationFullscreenAdQueue? = null
    private var fullscreenRewardedAdQueue: ChartboostMediationFullscreenAdQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        setupLogView()
        setupScreen()

        val interstitialPlacement =
            binding.interstitialPlacementName.text?.toString()
                ?: getString(R.string.heliumInterstitial)

        val rewardedPlacement =
            binding.rewardedPlacementName.text?.toString() ?: getString(R.string.heliumRewarded)

        binding.fullscreenSwitch.setOnCheckedChangeListener { _, isOn ->
            binding.fullscreenAdQueueSwitch.isChecked = isOn
            binding.fullscreenAdQueueSwitch.isEnabled = isOn

            if (isOn) {
                val isFullscreenQueue = binding.fullscreenAdQueueSwitch.isEnabled
                addToLogView("Using new Chartboost Mediation Fullscreen APIs.")
                setupFullScreenAd(interstitialPlacement, AdFormat.INTERSTITIAL, isFullscreenQueue)
                setupFullScreenAd(rewardedPlacement, AdFormat.REWARDED, isFullscreenQueue)
            } else {
                addToLogView("Using deprecated Chartboost Mediation Fullscreen APIs.")
                setupInterstitial(interstitialPlacement)
                setupRewarded(rewardedPlacement)
            }
        }

        binding.fullscreenAdQueueSwitch.apply {
            setOnCheckedChangeListener { _, isOn ->
                if (isOn) {
                    addToLogView("Using Chartboost Mediation Fullscreen Ad Queue to load ads.")
                    binding.btnLoad.text =
                        fullscreenInterstitialAdQueue?.let { queue -> if (!queue.isRunning) "START QUEUE" else "STOP QUEUE" }
                            ?: "LOAD"
                    binding.btnLoadRewarded.text =
                        fullscreenRewardedAdQueue?.let { queue -> if (!queue.isRunning) "START QUEUE" else "STOP QUEUE" }
                            ?: "LOAD"
                } else {
                    addToLogView("Using Chartboost Mediation Fullscreen APIs to load ads.")
                    resetButtons()
                }

                addToLogView("Using new Chartboost Mediation Fullscreen APIs.")
                setupFullScreenAd(interstitialPlacement, AdFormat.INTERSTITIAL, isOn)
                setupFullScreenAd(rewardedPlacement, AdFormat.REWARDED, isOn)
            }
        }

        setupInterstitial(interstitialPlacement)
        setupRewarded(rewardedPlacement)
        setupListeners()
        setupSdk()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bannerAd?.destroy()
        _bannerAd = null
        _interstitialAd?.destroy()
        _interstitialAd = null
        _rewardedAd?.destroy()
        _rewardedAd = null
        _fullscreenAd = null
    }

    private fun setupFullScreenAd(
        placement: String,
        adFormat: AdFormat,
        isFullscreenAdQueue: Boolean,
    ) {
        when (adFormat) {
            AdFormat.INTERSTITIAL -> {
                fullscreenInterstitialAdQueue =
                    ChartboostMediationFullscreenAdQueueManager.queue(this@MainActivity, placement)
                setUpLoadShowBinding(
                    placement,
                    binding.btnLoad,
                    binding.btnShow,
                    isFullscreenAdQueue,
                    fullscreenInterstitialAdQueue,
                )
            }

            AdFormat.REWARDED -> {
                fullscreenRewardedAdQueue =
                    ChartboostMediationFullscreenAdQueueManager.queue(this@MainActivity, placement)
                setUpLoadShowBinding(
                    placement,
                    binding.btnLoadRewarded,
                    binding.btnShowRewarded,
                    isFullscreenAdQueue,
                    fullscreenRewardedAdQueue,
                )
            }

            else -> return
        }
    }

    private fun setUpLoadShowBinding(
        placement: String,
        load: Button,
        showBtn: Button,
        isFullscreenAdQueue: Boolean,
        fullscreenAdQueue: ChartboostMediationFullscreenAdQueue?,
    ) {
        if (isFullscreenAdQueue) {
            load.apply {
                text =
                    fullscreenAdQueue?.let { queue -> if (!queue.isRunning) "START QUEUE" else "STOP QUEUE" }
                        ?: "LOAD"
                setOnClickListener {
                    fullscreenAdQueue?.let { queue ->
                        queue.adQueueListener = createFullscreenAdQueueListener(showBtn)
                        text =
                            if (!queue.isRunning) {
                                queue.start()
                                "STOP QUEUE"
                            } else {
                                queue.stop()
                                "START QUEUE"
                            }
                    }
                }
            }

            showBtn.apply {
                fullscreenAdQueue?.let { queue ->
                    text = context.getString(R.string.show, queue.numberOfAdsReady)
                    isEnabled = queue.numberOfAdsReady != 0
                    setOnClickListener {
                        if (queue.hasNextAd()) {
                            val ad = queue.getNextAd()
                            CoroutineScope(Main).launch {
                                showBtn.isEnabled = true
                                addToLogView("Queued ad bid info: ${ad?.winningBidInfo}")
                                ad?.listener = createFullscreenAdListener()
                                ad?.show(this@MainActivity)
                                    ?: addToLogView("Cannot show queued ad.")
                                text = context.getString(R.string.show, queue.numberOfAdsReady)
                            }
                        } else {
                            showBtn.isEnabled = queue.numberOfAdsReady != 0
                        }
                    }
                }
            }
        } else {
            load.setOnClickListener {
                CoroutineScope(Main).launch {
                    val request = ChartboostMediationAdLoadRequest(placement, Keywords())
                    _fullscreenAd = createFullScreenAd(request)
                    _fullscreenAd?.ad?.let { ad ->
                        showBtn.isEnabled = true
                        addToLogView("${ad.request.placementName} (FullscreenAd) cached with bid info: ${ad.winningBidInfo}")

                        showBtn.setOnClickListener {
                            CoroutineScope(Main).launch {
                                showBtn.isEnabled = false
                                ad.show(this@MainActivity)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupRewarded(placement: String) {
        _rewardedAd =
            createRewardedAd(placement)
                .also { newRewardedAd ->
                    binding.btnLoadRewarded.setOnClickListener { newRewardedAd.load() }

                    binding.btnShowRewarded.setOnClickListener {
                        if (newRewardedAd.readyToShow()) {
                            binding.btnShowRewarded.isEnabled = false
                            newRewardedAd.show()
                        } else {
                            addToLogView("Rewarded ad not ready to show")
                        }
                    }
                }
    }

    private fun setupInterstitial(placement: String) {
        _interstitialAd =
            createInterstitialAd(placement)
                .also { newInterstitialAd ->
                    binding.btnLoad.setOnClickListener { newInterstitialAd.load() }
                    binding.btnShow.setOnClickListener {
                        if (newInterstitialAd.readyToShow()) {
                            binding.btnShow.isEnabled = false
                            newInterstitialAd.show()
                        } else {
                            addToLogView("Interstitial ad not ready to show")
                        }
                    }
                }
    }

    private fun setupBanner() {
//        binding.bannerAd.let { bannerAd ->
//            bannerAd.heliumBannerAdListener = createBannerAd()
//            bannerAd.load()
//            _bannerAd = bannerAd
//        }
    }

    private fun createBannerAd(): HeliumBannerAdListener {
        val bannerListener =
            object : HeliumBannerAdListener {
                override fun onAdCached(
                    placementName: String,
                    loadId: String,
                    winningBidInfo: Map<String, String>,
                    error: ChartboostMediationAdException?,
                ) {
                    if (error != null) {
                        addToLogView("$placementName (HeliumBannerAd) onAdCached failed with heliumError: ${error.message}")
                    } else {
                        addToLogView("$placementName (HeliumBannerAd) onAdCached with bid info: $winningBidInfo")
                    }
                }

                override fun onAdClicked(placementName: String) {
                    addToLogView("$placementName (HeliumBannerAd) onAdClicked")
                }

                override fun onAdImpressionRecorded(placementName: String) {
                    addToLogView("$placementName (HeliumBannerAd) onAdImpressionRecorded")
                }
            }
        return bannerListener
    }

    private fun createInterstitialAd(interstitialPlacement: String): HeliumInterstitialAd {
        val interstitialAd =
            HeliumInterstitialAd(
                this,
                interstitialPlacement,
                object : HeliumFullscreenAdListener {
                    override fun onAdCached(
                        placementName: String,
                        loadId: String,
                        winningBidInfo: Map<String, String>,
                        error: ChartboostMediationAdException?,
                    ) {
                        if (error != null) {
                            addToLogView(
                                "$placementName (HeliumInterstitialAd) didCache failed with error cause: ${error.cause} message: ${error.message}",
                            )
                        } else {
                            Handler(Looper.getMainLooper()).post { binding.btnShow.isEnabled = true }
                            addToLogView("$placementName (HeliumInterstitialAd) didCache with bid info: $winningBidInfo")
                        }
                    }

                    override fun onAdClicked(placementName: String) {
                        addToLogView("$placementName (HeliumInterstitialAd) onAdClicked")
                    }

                    override fun onAdClosed(
                        placementName: String,
                        error: ChartboostMediationAdException?,
                    ) {
                        if (error != null) {
                            addToLogView("$placementName (HeliumInterstitialAd) onAdClosed failed with error: ${error.message}")
                        } else {
                            addToLogView("$placementName (HeliumInterstitialAd) onAdClosed")
                        }
                    }

                    override fun onAdImpressionRecorded(placementName: String) {
                        addToLogView("$placementName (HeliumInterstitialAd) onAdImpressionRecorded")
                    }

                    override fun onAdRewarded(placementName: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onAdShown(
                        placementName: String,
                        error: ChartboostMediationAdException?,
                    ) {
                        if (error != null) {
                            addToLogView("$placementName (HeliumInterstitialAd) didShow failed with error: ${error.message}")
                        } else {
                            addToLogView("$placementName (HeliumInterstitialAd) didShow")
                        }
                    }
                },
            )
        return interstitialAd
    }

    private fun createRewardedAd(rewardedPlacement: String): HeliumRewardedAd {
        val rewardedAd =
            HeliumRewardedAd(
                this,
                rewardedPlacement,
                object : HeliumFullscreenAdListener {
                    override fun onAdCached(
                        placementName: String,
                        loadId: String,
                        winningBidInfo: Map<String, String>,
                        error: ChartboostMediationAdException?,
                    ) {
                        if (error != null) {
                            addToLogView("$placementName (HeliumRewardedAd) didCache failed with error: ${error.message}")
                        } else {
                            Handler(Looper.getMainLooper()).post {
                                binding.btnShowRewarded.isEnabled = true
                            }
                            addToLogView("$placementName (HeliumRewardedAd) didCache with bid info: $winningBidInfo")
                        }
                    }

                    override fun onAdClicked(placementName: String) {
                        addToLogView("$placementName (HeliumRewardedAd) onAdClicked")
                    }

                    override fun onAdClosed(
                        placementName: String,
                        error: ChartboostMediationAdException?,
                    ) {
                        if (error != null) {
                            addToLogView("$placementName (HeliumRewardedAd) onAdClosed failed with error: ${error.message}")
                        } else {
                            addToLogView("$placementName (HeliumRewardedAd) onAdClosed")
                        }
                    }

                    override fun onAdImpressionRecorded(placementName: String) {
                        addToLogView("$placementName (HeliumRewardedAd) onAdImpressionRecorded")
                    }

                    override fun onAdRewarded(placementName: String) {
                        addToLogView("$placementName (HeliumRewardedAd) onAdRewarded: $placementName")
                    }

                    override fun onAdShown(
                        placementName: String,
                        error: ChartboostMediationAdException?,
                    ) {
                        if (error != null) {
                            addToLogView("$placementName (HeliumRewardedAd) onAdShown failed with error: ${error.message}")
                        } else {
                            addToLogView("$placementName (HeliumRewardedAd) onAdShown")
                        }
                    }
                },
            )
        return rewardedAd
    }

    private suspend fun createFullScreenAd(request: ChartboostMediationAdLoadRequest) =
        withContext(IO) {
            HeliumSdk.loadFullscreenAd(this@MainActivity, request, createFullscreenAdListener())
        }

    private fun setupScreen() {
        binding.btnShow.isEnabled = false
        binding.btnShowRewarded.isEnabled = false
        binding.interstitialPlacementName.text = getString(R.string.heliumInterstitial)
        binding.rewardedPlacementName.text = getString(R.string.heliumRewarded)
    }

    private fun setupLogView() {
        binding.logView.movementMethod = ScrollingMovementMethod.getInstance()
        binding.logView.isFocusable = true
        binding.logView.isFocusableInTouchMode = true
    }

    private fun addToLogView(s: String) {
        Handler(Looper.getMainLooper()).post {
            binding.logView.append(
                "\n$s\n",
            )
        }
    }

    private fun setupListeners() {
        binding.btnClean.setOnClickListener { binding.logView.text = "" }
    }

    private fun setupSdk() {
        CoroutineScope(Main).launch {
            HeliumSdk.start(
                this@MainActivity,
                getString(R.string.appID),
                getString(R.string.appSignature),
                heliumSdkListener =
                    object : HeliumSdk.HeliumSdkListener {
                        override fun didInitialize(error: Error?) {
                            if (error != null) {
                                val errorMessage =
                                    "Chartboost Mediation SDK failed to initialize. Reason: " + error.message
                                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                                    .show()
                                addToLogView("Chartboost Mediation SDK failed to initialize.")
                            } else {
                                addToLogView("Chartboost Mediation SDK (${HeliumSdk.getVersion()}) initialized successfully")
                                configSdk()
//                                setupBanner()
                            }
                        }
                    },
            )
            HeliumSdk.setDebugMode(true)
        }
    }

    private fun configSdk() {
        HeliumSdk.setTestMode(true)
        HeliumSdk.setSubjectToCoppa(false)
        HeliumSdk.setSubjectToGDPR(false)
        HeliumSdk.setUserHasGivenConsent(false)
        HeliumSdk.setCCPAConsent(true)
    }

    private fun createFullscreenAdListener() =
        object : ChartboostMediationFullscreenAdListener {
            override fun onAdClicked(ad: ChartboostMediationFullscreenAd) {
                addToLogView("${ad.request.placementName} (FullscreenAd) onAdClicked")
            }

            override fun onAdClosed(
                ad: ChartboostMediationFullscreenAd,
                error: ChartboostMediationAdException?,
            ) {
                if (error != null) {
                    addToLogView("${ad.request.placementName} (FullscreenAd) onAdClosed failed with error: ${error.message}")
                } else {
                    addToLogView("${ad.request.placementName} (FullscreenAd) onAdClosed")
                }
            }

            override fun onAdExpired(ad: ChartboostMediationFullscreenAd) {
                addToLogView("${ad.request.placementName} (FullscreenAd) onAdExpired")
            }

            override fun onAdImpressionRecorded(ad: ChartboostMediationFullscreenAd) {
                addToLogView("${ad.request.placementName} (FullscreenAd) onAdImpressionRecorded")
            }

            override fun onAdRewarded(ad: ChartboostMediationFullscreenAd) {
                addToLogView("${ad.request.placementName} (FullscreenAd) onAdRewarded")
            }
        }

    private fun createFullscreenAdQueueListener(showBtn: Button) =
        object : ChartboostMediationFullscreenAdQueueListener {
            override fun onFullScreenAdQueueUpdated(
                adQueue: ChartboostMediationFullscreenAdQueue,
                result: AdLoadResult,
                numberOfAdsReady: Int,
            ) {
                showBtn.isEnabled = numberOfAdsReady != 0
                showBtn.text = showBtn.context.getString(R.string.show, numberOfAdsReady)
                val resultError = result.error?.let { " with error $it" } ?: ""
                addToLogView("${adQueue.placementName} queue has been updated with $resultError. Number of ads ready: $numberOfAdsReady")
            }

            override fun onFullscreenAdQueueExpiredAdRemoved(
                adQueue: ChartboostMediationFullscreenAdQueue,
                numberOfAdsReady: Int,
            ) {
                addToLogView("${adQueue.placementName} queue had a queued ad expired. Number of ads ready: $numberOfAdsReady")
            }
        }

    private fun resetButtons() {
        binding.btnLoad.text = getString(R.string.str_load)
        binding.btnShow.text = getString(R.string.str_show)
        binding.btnShow.isEnabled = false
        binding.btnLoadRewarded.text = getString(R.string.str_load)
        binding.btnShowRewarded.text = getString(R.string.str_show)
        binding.btnShowRewarded.isEnabled = false
    }
}
