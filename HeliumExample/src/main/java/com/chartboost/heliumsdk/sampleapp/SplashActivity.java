package com.chartboost.heliumsdk.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chartboost.heliumsdk.HeliumSdk;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class SplashActivity extends Activity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("EA617F132FA0D9551CAEB6CD1A99A2BE")).build();
        MobileAds.setRequestConfiguration(configuration);

        HeliumSdk.start(this, getString(R.string.appID), getString(R.string.appSignature), error -> {
            if (error != null) {
                runOnUiThread(() -> {
                    String errorMessage = "Helium SDK failed to initialize. Reason: " + error.getMessage();
                    Toast.makeText(SplashActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                });
            } else {
                Intent mainActivityIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainActivityIntent);
                finish();
            }
        });

        HeliumSdk.setDebugMode(false);// Only for debug builds!
    }
}
