package com.chartboost.heliumsdk.sampleapp

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.chartboost.heliumsdk.HeliumSdk
import com.chartboost.heliumsdk.ad.*
import com.chartboost.heliumsdk.sampleapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding
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

        //val bannerAd=createBannerAd()
    }

    private fun setupRewarded() {
        val rewardedAd = createRewardedAd()
        binding.btnLoadRewarded.setOnClickListener(View.OnClickListener { rewardedAd.load() })

        binding.btnShowRewarded.setOnClickListener(View.OnClickListener {
            if (rewardedAd.readyToShow()) {
                binding.btnLoadRewarded.isEnabled = false
                rewardedAd.show()
            } else {
                addToLogView("Rewarded ad not ready to show")
            }
        })
    }

    private fun setupInterstitial() {
        val interstitialAd = createInterstitialAd()
        binding.btnLoad.setOnClickListener { interstitialAd.load() }
        binding.btnShow.setOnClickListener {
            if (interstitialAd.readyToShow()) {
                binding.btnShow.isEnabled = false
                interstitialAd.show()
            } else {
                addToLogView("Interstitial ad not ready to show")
            }
        }
    }

    private fun createBannerAd() {
        TODO("Not yet implemented")
    }

    private fun createInterstitialAd(): HeliumInterstitialAd {
        val interstitialPlacement = getString(R.string.heliumInterstitial)
        val interstitialAd =
            HeliumInterstitialAd(interstitialPlacement, object : HeliumInterstitialAdListener {
                override fun didReceiveWinningBid(
                    placementName: String,
                    bidInfo: HashMap<String, String>
                ) {
                    addToLogView("$placementName(HeliumInterstitialAd) didReceiveWinningBid")
                    addToLogView(bidInfo.toString())
                }

                override fun didCache(placementName: String, error: HeliumAdError?) {
                    if (error != null) {
                        addToLogView(placementName + "(HeliumInterstitialAd) didCache failed with error: " + error.getMessage())
                    } else {
                        Handler(Looper.getMainLooper()).post { binding.btnShow.isEnabled = true }
                        addToLogView("$placementName(HeliumInterstitialAd) didCache")
                    }
                }

                override fun didShow(placementName: String, error: HeliumAdError?) {
                    if (error != null) {
                        addToLogView(placementName + "(HeliumInterstitialAd) didShow failed with error: " + error.getMessage())
                    } else {
                        addToLogView("$placementName(HeliumInterstitialAd) didShow")
                    }
                }

                override fun didClose(placementName: String, error: HeliumAdError?) {
                    if (error != null) {
                        addToLogView(placementName + "(HeliumInterstitialAd) didClose failed with error: " + error.getMessage())
                    } else {
                        addToLogView("$placementName(HeliumInterstitialAd) didClose")
                    }
                }
            })
        return interstitialAd
    }

    private fun createRewardedAd(): HeliumRewardedAd {
        val rewardedPlacement = getString(R.string.heliumRewarded)
        val rewardedAd = HeliumRewardedAd(rewardedPlacement, object : HeliumRewardedAdListener {
            override fun didReceiveWinningBid(
                placementName: String,
                bidInfo: HashMap<String, String>
            ) {
                addToLogView("$placementName(HeliumRewardedAd) didReceiveWinningBid")
                addToLogView(bidInfo.toString())
            }

            override fun didCache(placementName: String, error: HeliumAdError?) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumRewardedAd) didCache failed with error: " + error.getMessage())
                } else {
                    Handler(Looper.getMainLooper()).post {
                        binding.btnShowRewarded.isEnabled = true
                    }
                    addToLogView("$placementName(HeliumRewardedAd) didCache")
                }
            }

            override fun didShow(placementName: String, error: HeliumAdError?) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumRewardedAd) didShow failed with error: " + error.getMessage())
                } else {
                    addToLogView("$placementName(HeliumRewardedAd) didShow")
                }
            }

            override fun didClose(placementName: String, error: HeliumAdError?) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumRewardedAd) didClose failed with error: " + error.getMessage())
                } else {
                    addToLogView("$placementName(HeliumRewardedAd) didClose")
                }
            }

            override fun didReceiveReward(placementName: String, reward: String) {
                addToLogView("$placementName(HeliumRewardedAd) didReceiveReward: $reward")
            }
        })
        return rewardedAd
    }

    private fun setupScreen() {
        binding.btnShow.isEnabled = false
        binding.btnShowRewarded.isEnabled = false
    }

    private fun setupLogView() {
        binding.logView.movementMethod = ScrollingMovementMethod.getInstance()
        binding.logView.isFocusable = true
        binding.logView.isFocusableInTouchMode = true
    }

    private fun addToLogView(s: String) {
        Handler(Looper.getMainLooper()).post {
            if (binding.logView != null) {
                binding.logView.append(
                    """$s""".trimIndent()
                )
            }
        }
    }

    private fun setupListeners() {
        binding.btnClean.setOnClickListener { binding.logView.text = "" }
    }

    private fun setupSdk() {
        HeliumSdk.setSubjectToCoppa(false)
        HeliumSdk.setSubjectToGDPR(false)
        HeliumSdk.setUserHasGivenConsent(false)
        HeliumSdk.setCCPAConsent(true)
        addToLogView("Helium SDK Started successfully")
    }

    override fun onBackPressed() {
        // If an interstitial is on screen, close it.
        if (HeliumSdk.onBackPressed()) return else super.onBackPressed()
    }
}