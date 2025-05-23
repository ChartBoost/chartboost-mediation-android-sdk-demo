package com.example.chartboost.mediation.sdk.demo.java.queuedads;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadRequest;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdView;
import com.chartboost.chartboostmediationsdk.domain.Keywords;
import com.example.chartboost.mediation.sdk.demo.java.R;
import com.example.chartboost.mediation.sdk.demo.java.databinding.ActivityQueuedAdsBinding;
import com.example.chartboost.mediation.sdk.demo.java.LogAdapter;

import java.util.ArrayList;

public class QueuedAdsActivity extends AppCompatActivity {

    private ActivityQueuedAdsBinding binding;
    private QueuedAdsViewModel viewModel;
    private String interstitialPlacementName;
    private String rewardedPlacementName;
    private RecyclerView logRecyclerView;
    private LogAdapter logAdapter;
    private ChartboostMediationBannerAdView banner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQueuedAdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(QueuedAdsViewModel.class);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.queued_ads);
        }

        interstitialPlacementName = getString(R.string.interstitial_placement);
        rewardedPlacementName = getString(R.string.rewarded_placement);

        viewModel.setup(interstitialPlacementName, rewardedPlacementName);
        viewModel.createAdQueue(this);

        setupLogsRecyclerView();
        setupObservers();
        setupClickListeners();
        loadBanner();
    }

    private void setupClickListeners() {
        // Interstitial Ad Buttons
        binding.startInterstitialQueueBtn.setOnClickListener(v -> {
            viewModel.startStopQueue(interstitialPlacementName);
        });

        binding.showInterstitialBtn.setOnClickListener(v ->
                viewModel.showFullscreenAd(interstitialPlacementName, this)
        );

        // Rewarded Ad Buttons
        binding.startRewardedQueueBtn.setOnClickListener(v -> {
            viewModel.startStopQueue(rewardedPlacementName);
        });

        binding.showRewardedBtn.setOnClickListener(v ->
                viewModel.showFullscreenAd(rewardedPlacementName, this)
        );

        // Logs Button
        binding.clearLogsBtn.setOnClickListener(v -> {
            viewModel.clearUiLogs();
        });
    }

    private void setupLogsRecyclerView() {
        logRecyclerView = binding.logsRv;
        logRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logAdapter = new LogAdapter(new ArrayList<>());
        logRecyclerView.setAdapter(logAdapter);
    }

    private void setupObservers() {
        viewModel.uiState.observe(this, uiState -> {
            if (uiState == null) return;

            if (uiState.interstitialQueueControlState == QueueControlState.START) {
                binding.startInterstitialQueueBtn.setText(R.string.start_queue_text);
            } else {
                binding.startInterstitialQueueBtn.setText(R.string.stop_queue_text);
            }

            if (uiState.rewardedQueueControlState == QueueControlState.START) {
                binding.startRewardedQueueBtn.setText(R.string.start_queue_text);
            } else {
                binding.startRewardedQueueBtn.setText(R.string.stop_queue_text);
            }

            binding.showInterstitialBtn.setEnabled(uiState.isShowInterstitialButtonEnabled);
            binding.showRewardedBtn.setEnabled(uiState.isShowRewardedButtonEnabled);
        });

        viewModel.logs.observe(this, logs -> {
            int size = logs.size();
            if (size == 0) {
                logAdapter.clearLogs();
                logRecyclerView.scrollToPosition(0);
            } else {
                logAdapter.setLogs(logs);
                logAdapter.notifyItemInserted(size - 1);
                logRecyclerView.post(() -> logRecyclerView.scrollToPosition(size - 1));
            }
        });
    }

    private void loadBanner() {
        viewModel.addToUiLogs("Loading banner started");
        String bannerPlacementName = getString(R.string.banner_placement);
        banner = new ChartboostMediationBannerAdView(
                this,
                bannerPlacementName,
                ChartboostMediationBannerAdView.ChartboostMediationBannerSize.STANDARD,
                viewModel.bannerAdListener
        );

        binding.bannerLayout.removeAllViews();
        binding.bannerLayout.addView(banner);
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
