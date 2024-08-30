Android Chartboost Mediation SDK
====

The Android Chartboost Mediation SDK, by Chartboost, is a Unified-Auction & Mediated solution which helps developers increase their mobile apps' revenue with the inclusion of other supported Programmatic & Mediated SDKs.

## Getting Started
---

### Before You Begin:
1. Have you set up your app with Chartboost Mediation?
2. Does your app have placements?

If not, simply add your app and create the placements in the Chartboost Mediation dashboard by following the [documentation here.](https://developers.chartboost.com/docs/mediation-import-apps)

### Add Gradle Dependencies:
To quickly get started, simply add the following dependencies inside your app's `build.gradle`:

```gradle
repositories {
    maven {
      name "Chartboost Mediation's maven repo"
      url "https://cboost.jfrog.io/artifactory/chartboost-mediation"
    }
    maven {
      name "Chartboost Core's maven repo"
      url "https://cboost.jfrog.io/artifactory/chartboost-core"
    }
}

dependencies {
    ...

	// Chartboost Mediation SDK
    implementation("com.chartboost:chartboost-mediation-sdk:5.+")
    implementation("com.chartboost:chartboost-core-sdk:1.+")
	
	// Chartboost Mediation Adapters
    implementation("com.chartboost:chartboost-mediation-adapter-chartboost:5.+")

    // Chartboost Mediation SDK Dependencies
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-common:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")

    ...
}
```

### Update your app's Manifest for Chartboost Mediation

```XML
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

These are optional
```XML
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

### Google Play Services

```Gradle
implementation "com.google.android.gms:play-services-base:18.0.1"
implementation "com.google.android.gms:play-services-ads-identifier:18.0.1"
```

## Start The Chartboost Mediation SDK:
---
In your application's `onCreate` method, start the Chartboost Mediation SDK by providing your app's app id, its app signature, and passing a _HeliumSdkListener_

```kotlin Kotlin
val configuration = SdkConfiguration(
    chartboostApplicationIdentifier = myHeliumAppId,
    modules = listOf()
)
ChartboostCore.initializeSdk(
    context,
    configuration,
    object : ModuleObserver {
        override fun onModuleInitializationCompleted(result: ModuleInitializationResult) {
            if (result.moduleId == ChartboostMediationSdk.CORE_MODULE_ID) {
	            if (result.exception != null) {
	                Log.d(TAG,"Chartboost Mediation SDK failed to initialize. Reason: ${result.exception.cause}");
	            } else {
	                //SDK Started,
	                Log.d(TAG,"Chartboost Mediation SDK initialized successfully");
	            }
            }
        }
    },
)
```

## Creating Fullscreen Placements

You can implement the HeliumFullscreenAdListener interface to receive notifications about interstitial and rewarded ads loading, displaying, and closing.

```kotlin Kotlin
private fun createFullscreenAdListener(): ChartboostMediationFullscreenAdListener =
    object : ChartboostMediationFullscreenAdListener {
        override fun onAdClicked(ad: ChartboostMediationFullscreenAd) {
            Log.d(TAG, "Fullscreen ad clicked")
        }

        override fun onAdClosed(
            ad: ChartboostMediationFullscreenAd,
            error: ChartboostMediationAdException?,
        ) {
            Log.d(TAG,
                "Fullscreen ad closed ${if (error != null) "with error: ${error.chartboostMediationError}" else "successfully"}",
            )
        }

        override fun onAdExpired(ad: ChartboostMediationFullscreenAd) {
            Log.d(TAG, "Fullscreen ad expired")
        }

        override fun onAdImpressionRecorded(ad: ChartboostMediationFullscreenAd) {
            Log.d(TAG, "Fullscreen ad impression recorded")
        }

        override fun onAdRewarded(ad: ChartboostMediationFullscreenAd) {
            Log.d(TAG, "Fullscreen ad rewarded")
        }
    }
```
## Loading & Showing Fullscreen Ads

To load a fullscreen ad,
```kotlin Kotlin
val request = ChartboostMediationFullscreenAdLoadRequest([Your Placement Name], Keywords())
val result =
    ChartboostMediationFullscreenAd.loadFullscreenAd(
        context,
        request,
        createFullscreenAdListener(),
    )
if (result.error != null) {
    Log.d(TAG, "Fullscreen ad failed to load with error: ${result.error?.cause}")
} else {
    fullscreenAd = result.ad
    Log.d(TAG, "Fullscreen ad loaded successfully")
}
```

To show the ad,
```kotlin Kotlin
val result = fullscreenAd?.show(context as Activity)

if (result?.error != null) {
    Log.d(TAG, "Fullscreen ad failed to show with error: ${result.error?.cause}")
} else {
    Log.d(TAG, "Fullscreen ad shown successfully")
}
```

## Creating Banner Placements
---
If you want banner ads, this is how to create, load, and show a banner ad. This is all done automatically.
```kotlin Kotlin
ChartboostMediationBannerAdView(
    context,
    placementName,
    size,
    object : ChartboostMediationBannerAdViewListener {
        override fun onAdClicked(placementName: String) {
            Log.d(TAG, "Banner ad clicked")
        }

        override fun onAdImpressionRecorded(placementName: String) {
            Log.d(TAG, "Banner ad impression recorded")
        }

        override fun onAdViewAdded(
            placement: String,
            child: View?,
        ) {
            Log.d(TAG, "Banner ad view added")
        }
    },
)
```

## Test Mode
---
To set the SDK to Test Mode, simply call the following method _after_ initializing the Chartboost Mediation SDK after its `start` method.
```kotlin Kotlin
// Start the Chartboost Core SDK first, before setting the sdk to Test Mode.
ChartboostCore.initializeSdk(...)
// Set the Chartboost Mediation SDK to Test Mode. Make sure to remove this before deployment.
ChartboostMediationSdk.setTestMode(context, true)
```

## COPPA, GDPR & Additional Methods:

---
For details on how to implement user privacy requirements, refer to the [Core SDK documentation](https://docs.chartboost.com/en/mediation/integrate/core/android/get-started/).

Note: Not all partner SDKs have full support for GDPR. Please refer to its official documentation for more info on how the sdk handles GDPR.

## 3rd-Party Supported Partner SDKs & Adapters
---
As of 5.0.0, the Chartboost Mediation SDK currently supports the following 3rd-party programmatic & mediated partner sdks:

* AdMob
* Amazon Publisher Services
* Applovin
* Digital Turbine Exchange
* Google Bidding
* inMobi
* ironSource
* Meta Audience Network
* Mintegral
* MobileFuse
* Pangle
* UnityAds
* Verve
* Vungle

To integrate, add the adapter you need by updating your app's build.gradle:
```Gradle

    implementation("com.chartboost:chartboost-mediation-adapter-admob:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-amazon-publisher-services:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-applovin:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-digital-turbine-exchange:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-google-bidding:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-hyprmx:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-inmobi:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-ironsource:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-meta-audience-network:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-mintegral:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-mobilefuse:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-pangle:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-unity-ads:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-verve:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-vungle:5.+")

```

Feel free to take a look at the Chartboost Mediation Demo app for an integration example

For more information, please read https://developers.chartboost.com/docs/get-started-with-mediation
