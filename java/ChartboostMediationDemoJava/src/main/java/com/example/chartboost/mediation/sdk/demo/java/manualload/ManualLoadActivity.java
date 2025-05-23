package com.example.chartboost.mediation.sdk.demo.java.manualload;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdLoadRequest;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationBannerAdView;
import com.chartboost.chartboostmediationsdk.ad.ChartboostMediationFullscreenAd;
import com.chartboost.chartboostmediationsdk.domain.Keywords;
import com.example.chartboost.mediation.sdk.demo.java.R;
import com.example.chartboost.mediation.sdk.demo.java.databinding.ActivityManualLoadBinding;
import com.example.chartboost.mediation.sdk.demo.java.LogAdapter;

import java.util.ArrayList;
import java.util.List;

public class ManualLoadActivity extends AppCompatActivity {

    private ManualLoadViewModel viewModel;
    private ActivityManualLoadBinding binding;
    private RecyclerView logRecyclerView;
    private LogAdapter logAdapter;
    private ChartboostMediationBannerAdView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityManualLoadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupLogsRecyclerView();

        viewModel = new ViewModelProvider(this).get(ManualLoadViewModel.class);
        viewModel.setup(getString(R.string.interstitial_placement), getString(R.string.rewarded_placement));

        viewModel.uiState.observe(this, manualLoadUIStateModel -> {
            binding.loadInterstitialBtn.setEnabled(manualLoadUIStateModel.isInterstitialLoadEnabled);
            binding.showInterstitialBtn.setEnabled(manualLoadUIStateModel.isInterstitialShowEnabled);
            binding.loadRewardedBtn.setEnabled(manualLoadUIStateModel.isRewardedLoadEnabled);
            binding.showRewardedBtn.setEnabled(manualLoadUIStateModel.isRewardedShowEnabled);
        });
        viewModel.uiLogs.observe(this, this::updateLogs);
        viewModel.showAdEvent.observe(this, event -> {
            ChartboostMediationFullscreenAd ad = event.getContentIfNotHandled();
            if (ad != null) {
                ad.showFullscreenAdFromJava(this, viewModel.getFullscreenAdShowListener(ad.getRequest().getPlacement()));
            }
        });

        setupOnClickListeners();
        loadBanner();
    }

    private void setupOnClickListeners() {
        binding.loadInterstitialBtn.setOnClickListener(v -> viewModel.loadInterstitial(this.getApplicationContext()));
        binding.loadRewardedBtn.setOnClickListener(v -> viewModel.loadRewarded(this.getApplicationContext()));
        binding.showInterstitialBtn.setOnClickListener(v -> viewModel.showInterstitial());
        binding.showRewardedBtn.setOnClickListener(v -> viewModel.showRewarded());
        binding.clearLogsBtn.setOnClickListener(v -> viewModel.clearUiLogs());
    }

    private void setupLogsRecyclerView() {
        logRecyclerView = binding.logsRv;
        logRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logAdapter = new LogAdapter(new ArrayList<>());
        logRecyclerView.setAdapter(logAdapter);
    }

    private void updateLogs(List<String> logs) {
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
