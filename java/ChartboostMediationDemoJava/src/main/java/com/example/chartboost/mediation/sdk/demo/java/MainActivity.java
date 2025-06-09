package com.example.chartboost.mediation.sdk.demo.java;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chartboost.mediation.sdk.demo.java.databinding.ActivityMainBinding;
import com.example.chartboost.mediation.sdk.demo.java.manualload.ManualLoadActivity;
import com.example.chartboost.mediation.sdk.demo.java.queuedads.QueuedAdsActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.manualLoadBtn.setOnClickListener((button) -> {
            openManualLoadAds();
        });
        binding.queuedAdsBtn.setOnClickListener((button) -> {
            openQueuedAdsClicked();
        });
    }

    public void openManualLoadAds() {
        Intent intent = new Intent(this, ManualLoadActivity.class);
        startActivity(intent);
    }

    public void openQueuedAdsClicked() {
        Intent intent = new Intent(this, QueuedAdsActivity.class);
        startActivity(intent);
    }
}
