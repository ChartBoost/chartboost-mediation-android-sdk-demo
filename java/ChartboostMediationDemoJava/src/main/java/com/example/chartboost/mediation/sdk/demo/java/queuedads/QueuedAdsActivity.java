package com.example.chartboost.mediation.sdk.demo.java.queuedads;


import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.chartboost.mediation.sdk.demo.java.BaseAdsActivity;
import com.example.chartboost.mediation.sdk.demo.java.R;
import com.example.chartboost.mediation.sdk.demo.java.databinding.ActivityQueuedAdsBinding;

public class QueuedAdsActivity extends BaseAdsActivity {

    private ActivityQueuedAdsBinding binding;

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

        viewModel.setup(interstitialPlacementName, rewardedPlacementName);
        ((QueuedAdsViewModel) viewModel).createAdQueue(this);

        setupLogsRecyclerView(binding.logsRv);
        setupObservers();
        setupClickListeners((QueuedAdsViewModel) viewModel);
        loadBanner(binding.bannerLayout);
    }

    private void setupClickListeners(final QueuedAdsViewModel viewModel) {
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

    @Override
    protected void setupObservers() {
        ((QueuedAdsViewModel) viewModel).uiState.observe(this, uiState -> {
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

        viewModel.uiLogs.observe(this, this::updateLogs);
    }
}
