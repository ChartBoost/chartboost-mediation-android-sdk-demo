Android Helium SDK
====

The Android Helium SDK, by Chartboost, is a Unified-Auction & Mediated solution which helps developers increase their mobile apps' revenue with the inclusion of other supported Programmatic & Mediated SDKs.

## Getting Started
---


### Before You Begin:
1. Have you set up your app with Helium?
2. Does your app have placements?

If not, simply add your app and create the placements in the Helium dashboard by following the [documentation here.](https://helium.chartboost.com/resources/docs/import-app)


### Add Gradle Dependencies:
To quickly get started, simply add the following dependencies inside your app's `build.gradle`:

```gradle
repositories {
  mavenCentral()
}

dependencies {
    ...

    implementation 'com.chartboost:helium:2.7.1'
    implementation 'org.greenrobot:eventbus:3.3.1'

    ...
}
```

### Update your app's Manifest for Helium

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

For Helium Chartboost, add the following:
```XML
<activity android:name="com.chartboost_helium.sdk.CBImpressionActivity"
    android:excludeFromRecents="true"
    android:hardwareAccelerated="true"
    android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
    android:configChanges="keyboardHidden|orientation|screenSize" />
```

If using mediation (i.e. Admob), you also need to include the 'classic' chartboost activity.
```XML
<activity android:name="com.chartboost.sdk.CBImpressionActivity"
    android:excludeFromRecents="true"
    android:hardwareAccelerated="true"
    android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
    android:configChanges="keyboardHidden|orientation|screenSize" />
```

### Google Play Services

```Gradle
implementation "com.google.android.gms:play-services-base:18.0.1"
implementation "com.google.android.gms:play-services-ads-identifier:18.0.1"
```

### Proguard

If using proguard, add the following to your `proguard-rules.pro` file.

```Java
-keep class com.chartboost.** { *; }
```

## Start The Helium SDK:
---
In your application's `onCreate` method, start the Helium SDK by providing your app's app id, its app signature, and passing a _HeliumSdkListener_

```Java
    HeliumSdk.start(this, "APP ID", "APP SIGNATURE",
        new HeliumSdk.HeliumSdkListener() {
            @Override
            public void didInitialize(Error error) {
                if (error != null) {
                    // Helium failed to initialize
                } else {
                    // Helium did initialize
                }
            }
        }
    );
```


## Creating Interstitial Placements
---
After initializing the Helium SDK, you want to create an interstitial ad object.

```Java
String interstitialPlacementName = "CBInterstitial";
HeliumInterstitialAd interstitialAd = new HeliumInterstitialAd(interstitialPlacementName,
    new HeliumInterstitialAdListener() {
        @Override
        public void didReceiveWinningBid(String placementName, HashMap<String, String> bidInfo) {}

        @Override
        public void didCache(String placementName, HeliumAdError error) {}

        @Override
        public void didShow(String placementName, HeliumAdError error) {}

        @Override
        public void didClose(String placementName, HeliumAdError error) {}
    }
);
```


## Loading & Showing Interstitial Ads
---
To load an interstitial ad, simply call the `HeliumInterstitialAd` you created
```Java
interstitialAd.load();
```

To show the ad, make sure the ad is `readyToShow`, then show the ad.
```Java
if (interstitialAd.readyToShow()) {
    interstitialAd.show();
}
```


## Creating Rewarded Placements
---
If you want rewarded ads, this is how to create a rewarded ad object.
```Java
String rewardedPlacementName = "CBRewarded";
HeliumRewardedAd rewardedAd = new HeliumRewardedAd(rewardedPlacementName,
    new HeliumRewardedAdListener() {
        @Override
        public void didReceiveWinningBid(String placementName, HashMap<String, String> bidInfo) {}

        @Override
        public void didCache(String placementName, HeliumAdError error) {}

        @Override
        public void didShow(String placementName, HeliumAdError error) {}

        @Override
        public void didClose(String placementName, HeliumAdError error) {}

        @Override
        public void didReceiveReward(String placementName, String reward) {}
    }
);
```


## Loading & Showing Rewarded Ads
---
To load a rewarded ad, simply call the `HeliumRewardedAd` you created
```Java
rewardedAd.load();
```

To show the ad, make sure the ad is `readyToShow`, then show the ad.
```Java
if (rewardedAd.readyToShow()) {
    rewardedAd.show();
}
```

## Creating Banner Placements
---
If you want banner ads, this is how to create a banner ad object.
```Java
/*
      The following Banner enum Sizes can be passed down:
      HeliumBannerAd.Size.STANDARD
      HeliumBannerAd.Size.MEDIUM
      HeliumBannerAd.Size.LEADERBOARD
    */
    HeliumBannerAd.Size bannerSize = HeliumBannerAd.Size.STANDARD;
    String bannerPlacementName = "CBBanner";
    HeliumBannerAd heliumBannerAd = new HeliumBannerAd(context, bannerPlacementName, bannerSize,
            new HeliumRewardedAdListener() {
                @Override
                public void didReceiveWinningBid(String placementName, HashMap<String, String> bidInfo) {}

                @Override
                public void didCache(String placementName, HeliumAdError error) {}

                @Override
                public void didShow(String placementName, HeliumAdError error) {}

                @Override
                public void didClose(String placementName, HeliumAdError error) {}
        }
    );

```

## Loading & Showing Banner Ads
---
To load a rewarded ad, simply call the `HeliumBannerAd` you created
```Java
bannerAd.load();
```

To show the ad, make sure the ad is `readyToShow`, then show the ad.
```Java
if (bannerAd.readyToShow()) {
    bannerAd.show();
}
```


## Test Mode
---
To set the SDK to Test Mode, simply call the following method _after_ initializing the Helium SDK after its `start` method.
```Java
// Start the Helium SDK first, before setting the sdk to Test Mode.
HeliumSdk.start(this, "APP ID", "APP SIG", sdkListener);
// Set the Helium SDK to Test Mode. Make sure to remove this before deployment.
HeliumSdk.setTestMode(true);
```


## COPPA, GDPR & Additional Methods:
---
To set the SDK for COPPA compliance:
```Java
HeliumSdk.setSubjectToCoppa(true);
```

The Helium SDK includes methods that set the GDPR setting as well as the user consent.
```Java
HeliumSdk.setSubjectToGDPR(true);
HeliumSdk.setUserHasGivenConsent(true);
```


In addition to the methods already mentioned above, the Helium SDK also provides the following methods:
```Java
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
As of 2.7.1, the Helium SDK currently supports the following 3rd-party programmatic & mediated partner sdks:

* Tapjoy
* Facebook
* AdColony
* AdMob
* Vungle
* Applovin
* UnityAds
* ironSource
* Fyber
* inMobi
* Mintegral

To integrate, add the adapter you need by updating your app's build.gradle:
```Gradle

//TapJoy
implementation 'com.chartboost:helium-tapjoy:2.7.1.0'
//Facebook
implementation 'com.chartboost:helium-facebook:2.7.1.0'
//AdColony
implementation 'com.chartboost:helium-adcolony:2.7.1.0'
//AdMob
implementation 'com.chartboost:helium-admob:2.7.1.0'
//Vungle
implementation 'com.chartboost:helium-vungle:2.7.1.0'
//Applovin
implementation 'com.chartboost:helium-applovin:2.7.1.0'
//UnityAds
implementation 'com.chartboost:helium-unityads:2.7.1.0'
//ironSource
implementation 'com.chartboost:helium-ironsource:2.7.1.0'
//Fyber
implementation 'com.chartboost:helium-fyber:2.7.1.0'
//inMobi
implementation 'com.chartboost:helium-inmobi:2.7.1.0'
//Mintegral
implementation 'com.chartboost:helium-mintegral:2.7.1.0'

//Make sure to also include the 3rd-party sdks & their dependencies
...
...
```



Feel free to take a look at the HeliumExample app for an integration example

For more information, please read https://helium.chartboost.com/resources/sdk
