package com.example.chartboost.mediation.sdk.demo.java; // Base package

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chartboost.chartboostmediationsdk.ChartboostMediationSdk;
import com.chartboost.core.ChartboostCore;
import com.chartboost.core.initialization.ModuleInitializationResult;
import com.chartboost.core.initialization.ModuleObserver;
import com.chartboost.core.initialization.SdkConfiguration;

import java.util.ArrayList;
import java.util.HashSet;

public class JavaDemoApp extends Application {

    private static final String TAG = "JavaDemoApp";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting Chartboost Mediation SDK initialization...");
        ChartboostCore.initializeSdkFromJava(this, new SdkConfiguration(getString(R.string.default_app_id), new ArrayList<>(), new HashSet<>()), new ModuleObserver() {
            @Override
            public void onModuleInitializationCompleted(@NonNull ModuleInitializationResult moduleInitializationResult) {
                if (moduleInitializationResult.getModuleId().equals(ChartboostMediationSdk.CORE_MODULE_ID)) {
                    if (moduleInitializationResult.getException() == null) {
                        Log.i(TAG, "Initialization finished");
                        if (BuildConfig.DEBUG) {
                            ChartboostMediationSdk.setTestMode(JavaDemoApp.this, true);
                            Log.i(TAG, "Test mode enabled");
                        } else {
                            ChartboostMediationSdk.setTestMode(JavaDemoApp.this, false);
                            Log.i(TAG, "Test mode disabled");
                        }
                        Toast.makeText(getBaseContext(), "Initialization finished", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Initialization failed", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

}
