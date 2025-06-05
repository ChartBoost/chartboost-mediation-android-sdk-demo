package com.example.chartboost.mediation.sdk.demo.java;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadRequest;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdView;
import com.chartboost.chartboostmediationsdk.domain.Keywords;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdsActivity extends AppCompatActivity {

    private RecyclerView logRecyclerView;
    private LogAdapter logAdapter;
    private ChartboostMediationBannerAdView banner;
    protected BaseAdsViewModel viewModel;

    protected String interstitialPlacementName;
    protected String rewardedPlacementName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        interstitialPlacementName = getString(R.string.interstitial_placement);
        rewardedPlacementName = getString(R.string.rewarded_placement);
    }

    protected void setupLogsRecyclerView(RecyclerView rv) {
        logRecyclerView = rv;
        logRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logAdapter = new LogAdapter(new ArrayList<>());
        logRecyclerView.setAdapter(logAdapter);
    }

    abstract protected void setupObservers();

    protected void updateLogs(List<String> logs) {
        int size = logs.size();
        if (size == 0) {
            logAdapter.clearLogs();
            logAdapter.notifyDataSetChanged();
            logRecyclerView.scrollToPosition(0);
        } else {
            logAdapter.setLogs(logs);
            logAdapter.notifyItemInserted(size - 1);
            logRecyclerView.post(() -> logRecyclerView.scrollToPosition(size - 1));
        }
    }

    protected void loadBanner(FrameLayout bannerFrameLayout) {
        viewModel.addToUiLogs("Loading banner started");
        String bannerPlacementName = getString(R.string.banner_placement);
        banner = new ChartboostMediationBannerAdView(
                this,
                bannerPlacementName,
                ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD,
                viewModel.bannerAdListener
        );

        bannerFrameLayout.removeAllViews();
        bannerFrameLayout.addView(banner);
        banner.loadFromJava(
                new ChartboostMediationBannerAdLoadRequest(bannerPlacementName, new Keywords(), ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD),
                viewModel.bannerAdLoadListener
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.invalidate();
    }
}
