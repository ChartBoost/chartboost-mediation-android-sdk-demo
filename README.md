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
  name "Chartboost Mediation's maven repo"
  url 'https://cboost.jfrog.io/artifactory/chartboost-mediation'
}

dependencies {
    ...

    implementation 'com.chartboost:chartboost-mediation-sdk:4.6.0'

    // Chartboost Mediation SDK Dependencies
    implementation 'com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.10.0'
    implementation 'com.squareup.okhttp3:okhttp:4.10.0'
    implementation 'com.squareup.retrofit2:converter-scalars:2.9.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1'

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

```java Java
HeliumSdk.start(
    MyActivityContext.this,
    myHeliumAppId,
    myHeliumAppSignature,
    new HeliumInitializationOptions(),
    new HeliumSdk.HeliumSdkListener() {
        @Override
        public void didInitialize(Error error) {
            if (error != null) {
                Log.d(TAG,"Chartboost Mediation SDK failed to initialize. Reason: " + error.getMessage());
            } else {
                //SDK Started,
                Log.d(TAG,"Chartboost Mediation SDK initialized successfully");
            }
        }
    }
);
```

## Creating Fullscreen Placements

You can implement the HeliumFullscreenAdListener interface to receive notifications about interstitial and rewarded ads loading, displaying, and closing.

```java Java
HeliumFullscreenAdListener heliumFullscreenAdListener = new HeliumFullscreenAdListener() {
    @Override
    public void onAdCached(@NonNull String placementName, @NonNull String loadId, @NonNull Map<String, String> winningBidInfo, @Nullable ChartboostMediationAdException error) {
        if (error != null) {
            Log.d(TAG, "Ad cache failed for placement: " + placementName + " reason: " + error.getMessage());
        } else {
            // Show the ad if it's ready
            Log.d(TAG, "Ad cached for placement: " + placementName);
        }
    }
 
    @Override
    public void onAdShown(@NonNull String placementName, @Nullable ChartboostMediationAdException error) {
        if (error != null) {
            Log.d(TAG, "Ad show failed for placement: " + placementName + " reason: " + error.getMessage());
        } else {
            Log.d(TAG, "Ad shown for placement: " + placementName);
        }
    }
 
    @Override
    public void onAdClicked(@NonNull String placementName) {
        Log.d(TAG, "Ad clicked for placement: " + placementName);
    }
 
    @Override
    public void onAdClosed(@NonNull String placementName, @Nullable ChartboostMediationAdException error) {
        if (error != null) {
            Log.d(TAG, "Ad closed with error for placement: " + placementName + " reason: " + error.getMessage());
        } else {
            Log.d(TAG, "Ad closed for placement: " + placementName);
        }
    }

    @Override
    public void onAdImpressionRecorded(@NonNull String placementName) {
        Log.d(TAG, "Ad recorded impression for placement: " + placementName);
    }

    @Override
    public void onAdRewarded(@NonNull String placementName) {
        Log.d(TAG, "Ad rewarded user for placement: " + placementName);
    }
};
```

## Loading & Showing Fullscreen Ads

To load a fullscreen ad,
```java Java
ChartboostMediationAdLoadRequest adRequest = new ChartboostMediationAdLoadRequest([Your Placement Name], new Keywords());
HeliumSdk.loadFullscreenAdFromJava(context, adRequest, heliumFullscreenAdListener);
```

To show the ad,
```java Java
// After loading an ad
public void onAdLoaded(ChartboostMediationFullscreenAd ad) {
    ad.showFullscreenAdFromJava(context, new ChartboostMediationFullscreenAdShowListener() {
        public void onAdShown(ChartboostMediationAdShowResult result) {
            // Shown
        }
    )
}
```

## Creating Interstitial Placements
---
After initializing the Chartboost Mediation SDK, you want to create an interstitial ad object.

```java Java
String interstitialPlacementName = "CBInterstitial";
HeliumInterstitialAd interstitialAd = new HeliumInterstitialAd(interstitialPlacementName,
    new HeliumInterstitialAdListener() {
        @Override
        public void didReceiveWinningBid(@NonNull String placementName, @NonNull HashMap bidInfo) {}
        
        @Override
        public void didCache(@NonNull String placementName, @Nullable HeliumAdError error) {}
     
        @Override
        public void didShow(@NonNull String placementName, @Nullable HeliumAdError error) {}
     
        @Override
        public void didClick(@NonNull String placementName, @Nullable HeliumAdError error) {}
     
        @Override
        public void didClose(@NonNull String placementName, @Nullable HeliumAdError error) {}
    
        @Override
        public void didRecordImpression(@NonNull String placementName) {}
    }
);
```

## Loading & Showing Interstitial Ads
---
To load an interstitial ad, simply call the `HeliumInterstitialAd` you created
```java Java
interstitialAd.load();
```

To show the ad, make sure the ad is `readyToShow`, then show the ad.
```java Java
if (interstitialAd.readyToShow()) {
    interstitialAd.show();
}
```

## Creating Rewarded Placements
---
If you want rewarded ads, this is how to create a rewarded ad object.
```java Java
String rewardedPlacementName = "CBRewarded";
HeliumRewardedAd rewardedAd = new HeliumRewardedAd(rewardedPlacementName,
    new HeliumRewardedAdListener() {
        @Override
        public void didReceiveWinningBid(@NonNull String placementName, @NonNull HashMap bidInfo) {}
     
        @Override
        public void didCache(@NonNull String placementName, @Nullable HeliumAdError error) {}
     
        @Override
        public void didShow(@NonNull String placementName, @Nullable HeliumAdError error) {}
     
        @Override
        public void didClick(@NonNull String placementName, @Nullable HeliumAdError error) {}
     
        @Override
        public void didClose(@NonNull String placementName, @Nullable HeliumAdError error) {}
     
        @Override
        public void didReceiveReward(@NonNull String placementName, @NonNull String reward) {}
    
        @Override
        public void didRecordImpression(@NonNull String placementName) {}
    }
);
```

## Loading & Showing Rewarded Ads
---
To load a rewarded ad, simply call the `HeliumRewardedAd` you created
```java Java
rewardedAd.load();
```

To show the ad, make sure the ad is `readyToShow`, then show the ad.
```java Java
if (rewardedAd.readyToShow()) {
    rewardedAd.show();
}
```

## Creating Banner Placements
---
If you want banner ads, this is how to create a banner ad object.
```java Java
/*
  The following Banner enum Sizes can be passed down:
  HeliumBannerAd.HeliumBannerSize.STANDARD
  HeliumBannerAd.HeliumBannerSize.MEDIUM
  HeliumBannerAd.HeliumBannerSize.LEADERBOARD
*/
HeliumBannerAd.HeliumBannerSize bannerSize = HeliumBannerAd.HeliumBannerSize.STANDARD;
String bannerPlacementName = "CBBanner";
HeliumBannerAd heliumBannerAd = new HeliumBannerAd(context, bannerPlacementName, bannerSize,
    new HeliumBannerAdListener() {
        @Override
        public void didReceiveWinningBid(@NonNull String placementName, @NonNull HashMap bindInfo) {}
    
        @Override
        public void didCache(@NonNull String placementName, @Nullable HeliumAdError error) {}
    
        @Override
        public void didClick(@NonNull String placementName, @Nullable HeliumAdError error) {}
    
        @Override
        public void didRecordImpression(@NonNull String placementName) {}
    }
);
```

## Loading & Showing Banner Ads
---
To load a banner ad, simply call the `HeliumBannerAd` you created
```java Java
bannerAd.load();
```

## Test Mode
---
To set the SDK to Test Mode, simply call the following method _after_ initializing the Chartboost Mediation SDK after its `start` method.
```java Java
// Start the Helium SDK first, before setting the sdk to Test Mode.
HeliumSdk.start(this, "APP ID", "APP SIG", sdkListener);
// Set the Helium SDK to Test Mode. Make sure to remove this before deployment.
HeliumSdk.setTestMode(true);
```

## COPPA, GDPR & Additional Methods:
---
To set the SDK for COPPA compliance:
```java Java
HeliumSdk.setSubjectToCoppa(true);
```

The Chartboost Mediation SDK includes methods that set the GDPR setting as well as the user consent.
```java Java
HeliumSdk.setSubjectToGDPR(true);
HeliumSdk.setUserHasGivenConsent(true);
```

In addition to the methods already mentioned above, the Chartboost Mediation SDK also provides the following methods:
```java Java
HeliumSdk.getAppId();
HeliumSdk.getAppSignature();
HeliumSdk.getContext();
HeliumSdk.getTestMode();
HeliumSdk.getVersion();
HeliumSdk.onBackPressed();
```

Note: Not all partner SDKs have full support for GDPR. Please refer to its official documentation for more info on how the sdk handles GDPR.

## 3rd-Party Supported Partner SDKs & Adapters
---
As of 4.0.0, the Chartboost Mediation SDK currently supports the following 3rd-party programmatic & mediated partner sdks:

* AdColony
* AdMob
* Amazon Publisher Services
* Applovin
* Digital Turbine Exchange
* Google Bidding
* inMobi
* ironSource
* Meta Audience Network
* Mintegral
* Pangle
* Tapjoy
* UnityAds
* Vungle
* Yahoo

To integrate, add the adapter you need by updating your app's build.gradle:
```Gradle

implementation 'com.chartboost:chartboost-mediation-adapter-adcolony:4.4.8.0.3'
implementation 'com.chartboost:chartboost-mediation-adapter-admob:4.22.3.0.3'
implementation 'com.chartboost:chartboost-mediation-adapter-amazon-publisher-services:4.9.8.5.0'
implementation 'com.chartboost:chartboost-mediation-adapter-applovin:4.11.11.2.3'
implementation 'com.chartboost:chartboost-mediation-adapter-chartboost:4.9.4.1.2'
implementation 'com.chartboost:chartboost-mediation-adapter-digital-turbine-exchange:4.8.2.4.0'
implementation 'com.chartboost:chartboost-mediation-adapter-google-bidding:4.22.3.0.3'
implementation 'com.chartboost:chartboost-mediation-adapter-inmobi:4.10.5.7.2'
implementation 'com.chartboost:chartboost-mediation-adapter-ironsource:4.7.5.1.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-meta-audience-network:4.6.16.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-mintegral:4.16.3.91.5'
implementation 'com.chartboost:chartboost-mediation-adapter-tapjoy:4.13.0.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-pangle:4.4.9.1.3.3'
implementation 'com.chartboost:chartboost-mediation-adapter-reference:4.1.0.1.2'
implementation 'com.chartboost:chartboost-mediation-adapter-unity-ads:4.4.8.0.2'
implementation 'com.chartboost:chartboost-mediation-adapter-vungle:4.7.0.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-yahoo:4.1.4.0.3'

```

Feel free to take a look at the Chartboost Mediation Demo app for an integration example

For more information, please read https://developers.chartboost.com/docs/get-started-with-mediation
