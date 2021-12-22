package com.chartboost.heliumsdk.sampleapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chartboost.heliumsdk.HeliumSdk

class SplashActivity : AppCompatActivity() {
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