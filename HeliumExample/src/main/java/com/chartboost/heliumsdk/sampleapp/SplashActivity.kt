package com.chartboost.heliumsdk.sampleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.chartboost.heliumsdk.HeliumSdk
import com.chartboost.heliumsdk.HeliumSdk.HeliumSdkListener
import android.widget.Toast
import com.chartboost.heliumsdk.sampleapp.MainActivity
import java.lang.Error

class SplashActivity : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        HeliumSdk.start(
            this,
            getString(R.string.appID),
            getString(R.string.appSignature)
        ) { error: Error? ->
            if (error != null) {
                runOnUiThread {
                    val errorMessage = "Helium SDK failed to initialize. Reason: " + error.message
                    Toast.makeText(this@SplashActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            } else {
                val mainActivityIntent = Intent(this@SplashActivity, MainActivity::class.java)
                this@SplashActivity.startActivity(mainActivityIntent)
                finish()
            }
        }
        HeliumSdk.setDebugMode(false) // Only for debug builds!
    }
}