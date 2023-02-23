package com.chartboost.heliumsdk.sampleapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chartboost.heliumsdk.HeliumSdk
import com.chartboost.heliumsdk.ad.*
import com.chartboost.heliumsdk.domain.ChartboostMediationAdException
import com.chartboost.mediation.sdk.sampleapp.R
import com.chartboost.mediation.sdk.sampleapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private var _bannerAd: HeliumBannerAd? = null
    private lateinit var binding: ActivityMainBinding
    private var _rewardedAd: HeliumRewardedAd? = null
    private var _interstitialAd: HeliumInterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        setupLogView()
        setupScreen()
        setupInterstitial()
        setupRewarded()
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
    }

    private fun setupRewarded() {
        _rewardedAd = createRewardedAd()
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

    private fun setupInterstitial() {
        _interstitialAd = createInterstitialAd()
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
        binding.bannerAd.let { bannerAd ->
            bannerAd.heliumBannerAdListener = createBannerAd()
            bannerAd.load()
            _bannerAd = bannerAd
        }
    }

    private fun createBannerAd(): HeliumBannerAdListener {
        val bannerListener = object : HeliumBannerAdListener {
            override fun onAdCached(
                placementName: String,
                loadId: String,
                winningBidInfo: Map<String, String>,
                error: ChartboostMediationAdException?
            ) {
                if (error != null) {
                    addToLogView("$placementName (HeliumBannerAd) onAdCached failed with heliumError: ${error.message}")
                } else {
                    addToLogView("$placementName (HeliumBannerAd) onAdCached")
                    addToLogView("$placementName (HeliumBannerAd) winningBidInfo $winningBidInfo")
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

    private fun createInterstitialAd(): HeliumInterstitialAd {
        val interstitialPlacement =
            binding.interstitialPlacementName.text?.toString()
                ?: getString(R.string.heliumInterstitial)
        val interstitialAd =
            HeliumInterstitialAd(this, interstitialPlacement, object : HeliumFullscreenAdListener {

                override fun onAdCached(
                    placementName: String,
                    loadId: String,
                    winningBidInfo: Map<String, String>,
                    error: ChartboostMediationAdException?
                ) {
                    if (error != null) {
                        addToLogView("$placementName (HeliumInterstitialAd) didCache failed with error: ${error.message}")
                    } else {
                        Handler(Looper.getMainLooper()).post { binding.btnShow.isEnabled = true }
                        addToLogView("$placementName (HeliumInterstitialAd) didCache")
                        addToLogView("$placementName (HeliumInterstitialAd) didReceiveWinningBid")
                        addToLogView(winningBidInfo.toString())
                    }
                }

                override fun onAdClicked(placementName: String) {
                    addToLogView("$placementName (HeliumInterstitialAd) onAdClicked")
                }

                override fun onAdClosed(
                    placementName: String,
                    error: ChartboostMediationAdException?
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
                    error: ChartboostMediationAdException?
                ) {
                    if (error != null) {
                        addToLogView("$placementName (HeliumInterstitialAd) didShow failed with error: ${error.message}")
                    } else {
                        addToLogView("$placementName (HeliumInterstitialAd) didShow")
                    }
                }
            })
        return interstitialAd
    }

    private fun createRewardedAd(): HeliumRewardedAd {
        val rewardedPlacement =
            binding.rewardedPlacementName.text?.toString() ?: getString(R.string.heliumRewarded)
        val rewardedAd = HeliumRewardedAd(this, rewardedPlacement, object : HeliumFullscreenAdListener {

            override fun onAdCached(
                placementName: String,
                loadId: String,
                winningBidInfo: Map<String, String>,
                error: ChartboostMediationAdException?
            ) {
                if (error != null) {
                    addToLogView("$placementName (HeliumRewardedAd) didCache failed with error: ${error.message}")
                } else {
                    Handler(Looper.getMainLooper()).post {
                        binding.btnShowRewarded.isEnabled = true
                    }
                    addToLogView("$placementName (HeliumRewardedAd) didCache")
                    addToLogView("$placementName (HeliumRewardedAd) didReceiveWinningBid $winningBidInfo")
                }
            }

            override fun onAdClicked(placementName: String) {
                addToLogView("$placementName (HeliumRewardedAd) onAdClicked")
            }

            override fun onAdClosed(placementName: String, error: ChartboostMediationAdException?) {
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

            override fun onAdShown(placementName: String, error: ChartboostMediationAdException?) {
                if (error != null) {
                    addToLogView("$placementName (HeliumRewardedAd) onAdShown failed with error: ${error.message}")
                } else {
                    addToLogView("$placementName (HeliumRewardedAd) onAdShown")
                }
            }
        })
        return rewardedAd
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
                "\n$s\n"
            )
        }
    }

    private fun setupListeners() {
        binding.btnClean.setOnClickListener { binding.logView.text = "" }
    }

    private fun setupSdk() {
        HeliumSdk.start(
            this, getString(R.string.appID),
            getString(R.string.appSignature)
        ) { error: Error? ->
            if (error != null) {
                runOnUiThread {
                    val errorMessage = "Helium SDK failed to initialize. Reason: " + error.message
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
                addToLogView("Helium SDK Started unsuccessfully")
            } else {
                addToLogView("Helium SDK Started successfully")
                configSdk()
                setupBanner()
            }
        }
    }

    private fun configSdk() {
        HeliumSdk.setDebugMode(false) // Only for debug builds!
        HeliumSdk.setSubjectToCoppa(false)
        HeliumSdk.setSubjectToGDPR(false)
        HeliumSdk.setUserHasGivenConsent(false)
        HeliumSdk.setCCPAConsent(true)
    }
}
