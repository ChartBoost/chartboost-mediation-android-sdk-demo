package com.chartboost.heliumsdk.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chartboost.heliumsdk.HeliumSdk;

import org.jetbrains.annotations.Nullable;


public class SplashActivity extends Activity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
