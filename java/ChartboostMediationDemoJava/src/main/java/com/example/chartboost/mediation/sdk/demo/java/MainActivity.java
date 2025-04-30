package com.example.chartboost.mediation.sdk.demo.java;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chartboost.mediation.sdk.demo.java.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UILogsListener, OnInitializationCompleteListener {

    private ActivityMainBinding binding;
    private final AdController adController = new AdController();
    private RecyclerView logRecyclerView;
    private LogAdapter logAdapter;
    private final List<String> uiLogs = new ArrayList<>();

    String interstitialPlacementName;
    String rewardedPlacementName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        interstitialPlacementName = getString(R.string.interstitial_placement);
        rewardedPlacementName = getString(R.string.rewarded_placement);
        setupLogsRecyclerView();

        adController.initializeChartboost(
                getString(R.string.default_app_id),
                this,
                getBaseContext(),
                this
        );

        binding.loadInterstitialBtn.setOnClickListener(v -> onLoadClick(interstitialPlacementName, (Button) v, binding.showInterstitialBtn));
        binding.showInterstitialBtn.setOnClickListener(v -> onShowClick(interstitialPlacementName, (Button) v));

        binding.loadRewardedBtn.setOnClickListener(v -> onLoadClick(rewardedPlacementName, (Button) v, binding.showRewardedBtn));
        binding.showRewardedBtn.setOnClickListener(v -> onShowClick(rewardedPlacementName, (Button) v));

        binding.queueSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            adController.handleQueueToggle(isChecked);
            addLog(String.format("Fullscreen Ad Queue will %s", isChecked ? "be used" : "not be used"));
        });
    }

    private void setupLogsRecyclerView() {
        logRecyclerView = binding.logsRv;
        Button clearLogsButton = binding.clearLogsBtn;
        logRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logAdapter = new LogAdapter(uiLogs);
        logRecyclerView.setAdapter(logAdapter);
        clearLogsButton.setOnClickListener(v -> clearLogs());
    }

    private void onLoadClick(String placementName, Button loadButton, Button correspondingShowButton) {
        loadButton.setEnabled(false);
        adController.loadFullscreenAd(placementName, getBaseContext(), loadButton, correspondingShowButton, this);
    }

    private void onShowClick(String interstitialPlacementName, Button showButton) {
        adController.showFullscreenAd(interstitialPlacementName, showButton, MainActivity.this, this);
    }

    private void loadBanner() {
        adController.loadBanner(getString(R.string.banner_placement), this, binding.bannerLayout, getBaseContext());
    }

    private void addLog(String log) {
        uiLogs.add(log);
        logAdapter.notifyItemInserted(uiLogs.size() - 1);
        logRecyclerView.post(() -> logRecyclerView.scrollToPosition(uiLogs.size() - 1));
    }

    private void clearLogs() {
        uiLogs.clear();
        logAdapter.notifyDataSetChanged();
    }

    @Override
    public void add(String log) {
        addLog(log);
    }

    @Override
    public void onInitializationCompleted() {
        loadBanner();
        adController.createAdQueue(interstitialPlacementName, binding.showInterstitialBtn, getBaseContext(), this);
        adController.createAdQueue(rewardedPlacementName, binding.showRewardedBtn, getBaseContext(), this);
    }

    @Override
    public void onInitializationException() {
        Toast.makeText(getBaseContext(), "Initialization failed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adController.clear();
    }
}
