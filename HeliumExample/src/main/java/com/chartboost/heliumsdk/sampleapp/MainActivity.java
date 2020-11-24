package com.chartboost.heliumsdk.sampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chartboost.heliumsdk.HeliumSdk;
import com.chartboost.heliumsdk.ad.HeliumAdError;
import com.chartboost.heliumsdk.ad.HeliumInterstitialAd;
import com.chartboost.heliumsdk.ad.HeliumInterstitialAdListener;
import com.chartboost.heliumsdk.ad.HeliumRewardedAd;
import com.chartboost.heliumsdk.ad.HeliumRewardedAdListener;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class MainActivity extends Activity {

    private TextView logView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        HeliumSdk.setTestMode(false);
        this.logView = findViewById(R.id.logView);
        logView.setMovementMethod(ScrollingMovementMethod.getInstance());
        logView.setFocusable(true);
        logView.setFocusableInTouchMode(true);

        String interstitialPlacement = getString(R.string.heliumInterstitial);
        String rewardedPlacement = getString(R.string.heliumRewarded);

        final Button btnLoad = findViewById(R.id.btn_load);
        final Button btnShow = findViewById(R.id.btn_show);
        btnShow.setEnabled(false);

        final Button btnLoadReward = findViewById(R.id.btn_load_rewarded);
        final Button btnShowReward = findViewById(R.id.btn_show_rewarded);
        btnShowReward.setEnabled(false);


        final HeliumInterstitialAd interstitialAd = new HeliumInterstitialAd(interstitialPlacement, new HeliumInterstitialAdListener() {

            @Override
            public void didReceiveWinningBid(String placementName, HashMap<String, String> bidInfo) {
                addToLogView(placementName + "(HeliumInterstitialAd) didReceiveWinningBid");
                addToLogView(bidInfo.toString());
            }

            @Override
            public void didCache(String placementName, HeliumAdError error) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumInterstitialAd) didCache failed with error: " + error.getMessage());
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            btnShow.setEnabled(true);
                        }
                    });
                    addToLogView(placementName + "(HeliumInterstitialAd) didCache");
                }
            }

            @Override
            public void didShow(String placementName, HeliumAdError error) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumInterstitialAd) didShow failed with error: " + error.getMessage());
                } else {
                    addToLogView(placementName + "(HeliumInterstitialAd) didShow");
                }
            }

            @Override
            public void didClose(String placementName, HeliumAdError error) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumInterstitialAd) didClose failed with error: " + error.getMessage());
                } else {
                    addToLogView(placementName + "(HeliumInterstitialAd) didClose");
                }
            }
        });

        final HeliumRewardedAd rewardedAd = new HeliumRewardedAd(rewardedPlacement, new HeliumRewardedAdListener() {
            @Override
            public void didReceiveWinningBid(String placementName, HashMap<String, String> bidInfo) {
                addToLogView(placementName + "(HeliumRewardedAd) didReceiveWinningBid");
                addToLogView(bidInfo.toString());
            }

            @Override
            public void didCache(String placementName, HeliumAdError error) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumRewardedAd) didCache failed with error: " + error.getMessage());
                } else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            btnShowReward.setEnabled(true);
                        }
                    });
                    addToLogView(placementName + "(HeliumRewardedAd) didCache");
                }
            }

            @Override
            public void didShow(String placementName, HeliumAdError error) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumRewardedAd) didShow failed with error: " + error.getMessage());
                } else {
                    addToLogView(placementName + "(HeliumRewardedAd) didShow");
                }
            }

            @Override
            public void didClose(String placementName, HeliumAdError error) {
                if (error != null) {
                    addToLogView(placementName + "(HeliumRewardedAd) didClose failed with error: " + error.getMessage());
                } else {
                    addToLogView(placementName + "(HeliumRewardedAd) didClose");
                }
            }

            @Override
            public void didReceiveReward(String placementName, String reward) {
                addToLogView(placementName + "(HeliumRewardedAd) didReceiveReward: " + reward);
            }
        });

        btnLoad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitialAd.load();
            }
        });


        btnShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.readyToShow()) {
                    btnShow.setEnabled(false);
                    interstitialAd.show();
                } else {
                    addToLogView("Interstitial ad not ready to show");
                }
            }
        });

        btnLoadReward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardedAd.load();
            }
        });

        btnShowReward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd.readyToShow()) {
                    btnShowReward.setEnabled(false);
                    rewardedAd.show();
                } else {
                    addToLogView("Rewarded ad not ready to show");
                }
            }
        });

        Button logClean = findViewById(R.id.btn_clean);
        logClean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                logView.setText("");
            }
        });

        HeliumSdk.setSubjectToCoppa(false);
        HeliumSdk.setSubjectToGDPR(false);
        HeliumSdk.setUserHasGivenConsent(false);
        HeliumSdk.setCCPAConsent(true);
        addToLogView("Helium SDK Started successfully");
    }

    @Override
    public void onBackPressed() {
        // If an interstitial is on screen, close it.
        if (HeliumSdk.onBackPressed())
            return;
        else
            super.onBackPressed();
    }

    private void addToLogView(final String s) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.this.logView != null) {
                    MainActivity.this.logView.append("\n" + s);
                }
            }
        });
    }
}
