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

    implementation 'com.chartboost:chartboost-mediation-sdk:4.0.0'
    implementation 'org.greenrobot:eventbus:3.3.1'

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
        public void didClick(String placementName, HeliumAdError error) {}

        @Override
        public void didClose(String placementName, HeliumAdError error) {}

        @Override
        public void didRecordImpression(String placementName) {}
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
        public void didClick(String placementName, HeliumAdError error) {}

        @Override
        public void didClose(String placementName, HeliumAdError error) {}

        @Override
        public void didReceiveReward(String placementName, String reward) {}

        @Override
        public void didRecordImpression(String placementName) {}
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
                public void didClick(String placementName, HeliumAdError error) {}

                @Override
                public void didRecordImpression(String placementName) {}
        }
    );

```

## Loading & Showing Banner Ads
---
To load a rewarded ad, simply call the `HeliumBannerAd` you created
```Java
bannerAd.load();
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
As of 2.9.0, the Helium SDK currently supports the following 3rd-party programmatic & mediated partner sdks:

* AdColony
* AdMob
* Applovin
* Meta Audience Network
* Digital Turbine Exchange
* Google Bidding
* inMobi
* ironSource
* Mintegral
* Tapjoy
* Pangle
* UnityAds
* Vungle
* Yahoo

To integrate, add the adapter you need by updating your app's build.gradle:
```Gradle

implementation 'com.chartboost:chartboost-mediation-adapter-adcolony:4.4.8.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-admob:4.21.3.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-applovin:4.11.5.5.0'
implementation 'com.chartboost:chartboost-mediation-adapter-chartboost:4.9.2.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-amazon-publisher-services:4.9.6.2.0'
implementation 'com.chartboost:chartboost-mediation-adapter-digital-turbine-exchange:4.8.2.1.0'
implementation 'com.chartboost:chartboost-mediation-adapter-google-bidding:4.21.3.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-inmobi:4.10.1.1.0'
implementation 'com.chartboost:chartboost-mediation-adapter-ironsource:4.7.2.5.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-meta-audience-network:4.6.12.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-mintegral:4.16.0.31.0'
implementation 'com.chartboost:chartboost-mediation-adapter-tapjoy:4.12.9.1.0'
implementation 'com.chartboost:chartboost-mediation-adapter-pangle:4.4.3.0.4.0'
implementation 'com.chartboost:chartboost-mediation-adapter-reference:4.1.0.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-unity-ads:4.4.4.1.0'
implementation 'com.chartboost:chartboost-mediation-adapter-vungle:4.6.12.0.0'
implementation 'com.chartboost:chartboost-mediation-adapter-yahoo:4.1.3.0.0'

```



Feel free to take a look at the HeliumExample app for an integration example

For more information, please read https://helium.chartboost.com/resources/sdk
