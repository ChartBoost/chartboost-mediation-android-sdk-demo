package com.example.chartboost.mediation.sdk.demo.java.manualload;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.example.chartboost.mediation.sdk.demo.java.BaseAdsActivity;
import com.example.chartboost.mediation.sdk.demo.java.R;
import com.example.chartboost.mediation.sdk.demo.java.databinding.ActivityManualLoadBinding;

public class ManualLoadActivity extends BaseAdsActivity {

    private ActivityManualLoadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityManualLoadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupLogsRecyclerView(binding.logsRv);

        viewModel = new ViewModelProvider(this).get(ManualLoadViewModel.class);
        viewModel.setup(getString(R.string.interstitial_placement), getString(R.string.rewarded_placement));

        setupObservers();
        setupOnClickListeners((ManualLoadViewModel) viewModel);
        loadBanner(binding.bannerLayout);
    }

    protected void setupObservers() {
        ((ManualLoadViewModel) viewModel).uiState.observe(this, manualLoadUIStateModel -> {
            binding.loadInterstitialBtn.setEnabled(manualLoadUIStateModel.isInterstitialLoadEnabled);
            binding.showInterstitialBtn.setEnabled(manualLoadUIStateModel.isInterstitialShowEnabled);
            binding.loadRewardedBtn.setEnabled(manualLoadUIStateModel.isRewardedLoadEnabled);
            binding.showRewardedBtn.setEnabled(manualLoadUIStateModel.isRewardedShowEnabled);
        });
        viewModel.uiLogs.observe(this, this::updateLogs);
        ((ManualLoadViewModel) viewModel).showAdEvent.observe(this, event -> {
            ChartboostMediationFullscreenAd ad = event.getContentIfNotHandled();
            if (ad != null) {
                ad.showFullscreenAdFromJava(this, ((ManualLoadViewModel) viewModel).getFullscreenAdShowListener(ad.getRequest().getPlacement()));
            }
        });
    }

    private void setupOnClickListeners(ManualLoadViewModel viewModel) {
        binding.loadInterstitialBtn.setOnClickListener(v -> viewModel.loadInterstitial(this.getApplicationContext()));
        binding.loadRewardedBtn.setOnClickListener(v -> viewModel.loadRewarded(this.getApplicationContext()));
        binding.showInterstitialBtn.setOnClickListener(v -> viewModel.showInterstitial());
        binding.showRewardedBtn.setOnClickListener(v -> viewModel.showRewarded());
        binding.clearLogsBtn.setOnClickListener(v -> viewModel.clearUiLogs());
    }
}
