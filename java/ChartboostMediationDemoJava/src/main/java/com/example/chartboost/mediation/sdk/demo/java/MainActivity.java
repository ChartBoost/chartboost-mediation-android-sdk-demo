package com.example.chartboost.mediation.sdk.demo.java;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chartboost.mediation.sdk.demo.java.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnLogStateChangeListener {

    private ActivityMainBinding binding;
    private AdController adController = new AdController();
    private final static String TAG = "MainActivity";
    private RecyclerView logRecyclerView;
    private LogAdapter logAdapter;
    private final List<String> logs = new ArrayList<>();

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
                this::loadBanner
        );

        binding.loadInterstitialBtn.setOnClickListener(v -> onLoadClick(interstitialPlacementName, binding.showInterstitialBtn));
        binding.showInterstitialBtn.setOnClickListener(v -> onShowClick(interstitialPlacementName, (Button) v));

        binding.loadRewardedBtn.setOnClickListener(v -> onLoadClick(rewardedPlacementName, binding.showRewardedBtn));
        binding.showRewardedBtn.setOnClickListener(v -> onShowClick(rewardedPlacementName, (Button) v));

        binding.queueSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            adController.setShouldUseQueue(isChecked);
            addLog(String.format("Fullscreen Ad Queue will %s", isChecked ? "be used" : "not be used"));
        });
    }

    private void setupLogsRecyclerView() {
        logRecyclerView = binding.logsRv;
        Button clearLogsButton = binding.clearLogsBtn;
        logRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        logAdapter = new LogAdapter(logs);
        logRecyclerView.setAdapter(logAdapter);
        clearLogsButton.setOnClickListener(v -> clearLogs());
    }

    private void onLoadClick(String placementName, Button correspondingShowButton) {
        adController.loadFullscreenAd(placementName, getBaseContext(), correspondingShowButton, this);
    }

    private void onShowClick(String interstitialPlacementName, Button showButton) {
        adController.showFullscreenAd(interstitialPlacementName, showButton, MainActivity.this, this);
    }

    private void loadBanner() {
        adController.loadBanner(getString(R.string.banner_placement), this, binding.bannerLayout, getBaseContext());
    }

    private void addLog(String log) {
        logs.add(log);
        logAdapter.notifyItemInserted(logs.size() - 1);
        logRecyclerView.post(() -> logRecyclerView.scrollToPosition(logs.size() - 1));
    }

    private void clearLogs() {
        logs.clear();
        logAdapter.notifyDataSetChanged();
    }

    @Override
    public void logState(String log) {
        addLog(log);
    }

    public interface OnInitializationCompleteListener {
        void onInitializationCompleted();
    }
}
