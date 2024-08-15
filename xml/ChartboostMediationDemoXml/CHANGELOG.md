Android Change Log
==================
Check for the latest Chartboost Mediation SDK at the Chartboost Mediation website.

### Version 4.5.0 *(2023-08-31)*
Improvements:
- TCFv2 String is now read from `SharedPreferences` and passed in the auction request. Publishers do not need to take any additional steps.

Bug Fixes:
- `line_item_name` in `onAdCached()` no longer returns values with extra quotes.

### Version 4.4.1 *(2023-08-03)*
Bug Fixes:
- Fix missing `onAdImpressionRecorded` call in `ChartboostMediationFullscreenAd`.

### Version 4.4.0 *(2023-07-27)*
Bug Fixes:
- Fixed an issue that prevented setting `customData` for the recently introduced `ChartboostMediationFullscreenAd`.

### Version 4.3.0 *(2023-06-22)*
Improvements:
- Added support for Rewarded Interstitials. This is available via `HeliumSdk.loadFullscreenAd()` and supported only in the latest adapters. Please check each adapter's changelog to confirm which partners support rewarded interstitials.
- Added new `ChartboostMediationFullscreenAd` APIs which combine and improve the interstitial and rewarded ad APIs. Previous interstitial and rewarded ad APIs are now deprecated.
- Added `line_item_name` and `line_item_id` to `winningBidInfo`.
- Added the ability to set consent and debug flags before calling `start()`. Only the last change is applied and will only be updated after the SDK has finished initializing.

### Version 4.2.0 *(2023-05-04)*
Improvements:
- Added support for multiple banner instances with the same Chartboost Mediation placement.
- Added error code `CM_335` to indicate a situation where all items in the waterfall have been exhausted.

### Version 4.1.0 *(2023-03-30)*
Improvements:
- Added `HeliumSdk.getAdapterInfo()` to get a list of initialized adapters.
- Added CM_115 error code for Mediation initialization failure.
- Improved networking to have a more robust server communication layer. This adds Retrofit and some other related dependencies.
- Removed the Event Bus dependency.

Bug Fixes:
- Fixed a bug where a show failure would not allow future loading of a fullscreen placement unless it was cleared.

### Version 4.0.0 *(2023-02-23)*

As part of the Marketing team’s efforts to clearly articulate the use cases and customers we support by being more descriptive with our product branding, **Helium is being rebranded as Chartboost Mediation.**

Starting in 4.0.0, the Chartboost Mediation brand will be used in place of Helium for new additions. In the coming 4.X releases, the old Helium branding will be deprecated and the new Chartboost Mediation branding will be used to give publishers a smoother transition.

Improvements:
- Renamed the maven dependency to `com.chartboost:chartboost-mediation-sdk`.
- Revamped partner adapter APIs. Partner adapters are now open sourced and hosted on individual git repositories. Find the full partner list and more information on how to use the new adapters [here](https://developers.chartboost.com/docs/mediation-android-get-started).
- Renamed `HeliumError` to `ChartboostMediationAdException` and added more details to better identify the reason for failures and provide relevant context.
- Unified interstitial and rewarded listeners into `HeliumFullscreenAdListener`.
- Removed the `reward` parameter from the `HeliumFullscreenAdListener`.
- Unified the bid win and load callback into one `onAdCached` callback for `HeliumBannerAdListener` and `HeliumFullscreenAdListener`.
- Moved the `loadId` from the return value of `load()` to their respective listeners’ `onAdCached()`.
- `clearAd()` and `clearLoaded()` methods no longer return a value.
- Removed `onBackPressed()` from `HeliumSdk`.
- Google bidding `QueryInfo` timeout is now set in the Google bidding adapter (from 15 to 3 minutes).

Bug Fixes:
- Improved loading and caching ads, especially for banners. There should be fewer issues loading ads concurrently and during auto refreshing banners.

### Version 3.3.1 *(2023-01-09)*
Bug Fixes:
- Fixed banner auto refresh settings not applying.

This version of the Helium SDK includes support for the following Ad Providers:
>- *AdColony: 4.8.0*
>- *AdMob: 21.3.0*
>- *AppLovin: 11.5.5*
>- *Chartboost: 9.1.1*
>- *Facebook Audience Network: 6.12.0*
>- *Fyber Marketplace: 8.2.1*
>- *Google bidding: 21.3.0*
>- *InMobi: 10.1.1*
>- *ironSource: 7.2.5*
>- *Mintegral: 16.0.31*
>- *TapJoy: 12.9.1*
>- *UnityAds: 4.4.1*
>- *Vungle: 6.12.0*
>- *Yahoo: 1.3.0*

### Version 3.3.0 *(2022-12-01)*
Improvements:
- Added ability to not initialize certain partners. `HeliumSdk#start` now can take `HeliumInitializationOptions` with a `Set<String>` of skipped partner IDs to not initialize a particular partner.
- Added several more metrics around ad lifecycle and initialization. `HeliumSdk#subscribeInitializationResults` for details for which partners initialized and how long it took.
- Verified support for Android 13 (API level 33).

This version of the Helium SDK includes support for the following Ad Providers:
>- *AdColony: 4.8.0*
>- *AdMob: 21.3.0*
>- *AppLovin: 11.5.5*
>- *Chartboost: 9.1.1*
>- *Facebook Audience Network: 6.12.0*
>- *Fyber Marketplace: 8.2.1*
>- *Google bidding: 21.3.0*
>- *InMobi: 10.1.1*
>- *ironSource: 7.2.5*
>- *Mintegral: 16.0.31*
>- *TapJoy: 12.9.1*
>- *UnityAds: 4.4.1*
>- *Vungle: 6.12.0*
>- *Yahoo: 1.3.0*

### Version 3.2.0 *(2022-10-20)*
Bug Fixes:
- Fixed potential null pointers when accessing `Ad.bids`.

This version of the Helium SDK includes support for the following Ad Providers:
>- *AdColony: 4.7.0*
>- *AdMob: 20.6.0*
>- *AppLovin: 11.3.1*
>- *Chartboost: 8.3.1*
>- *Facebook Audience Network: 6.8.0*
>- *Fyber Marketplace: 8.1.3*
>- *Google bidding: 20.6.0*
>- *InMobi: 10.0.5*
>- *ironSource: 7.2.1*
>- *Mintegral: 16.0.31*
>- *TapJoy: 12.9.1*
>- *UnityAds: 4.2.1*
>- *Vungle: 6.10.5*
>- *Yahoo: 1.14.0*

### Version 3.1.0 *(2022-09-22)*
Improvements:
- Rate limiting added to all ad requests.
- Removed the usage of proguard for Helium and partner adapters.

This version of the Helium SDK includes support for the following Ad Providers:

>- *AdColony: 4.7.0*
>- *AdMob: 20.6.0*
>- *AppLovin: 11.3.1*
>- *Chartboost: 8.3.1*
>- *Facebook Audience Network: 6.8.0*
>- *Fyber Marketplace: 8.1.3*
>- *Google bidding: 20.6.0*
>- *InMobi: 10.0.5*
>- *ironSource: 7.2.1*
>- *Mintegral: 16.0.31*
>- *TapJoy: 12.9.1*
>- *UnityAds: 4.2.1*
>- *Vungle: 6.10.5*
>- *Yahoo: 1.14.0*

### Version 3.0.0 *(2022-08-18)*
Improvements:
- Banner API updated from a load-show to a load-only paradigm. A summary of the changes is listed below:
  - `HeliumBannerAd`
    - `load` now returns the load identifier.
    - `clearLoaded` renamed to `clearAd`
    - `show` removed. Banners are ready to show upon successful load.
    - `readyToShow` removed since it is no longer necessary.
  - `HeliumBannerAdListener`
    - `didShow` removed since it is no longer necessary.
    - `didClose` removed.
    - `didReceiveWinningBid` will only be invoked when automatic refresh has been disabled for the banner placement.
- Helium impression events are now separate from partner network impression events.
- `HeliumBannerAdListener`, `HeliumInterstitialAdListener`, and `HeliumRewardedAdListener` now have `didRecordImpression(placementName: String)`. These are for Helium impression events.
- All ad formats now load their waterfalls in a sequential manner.

Bug Fixes:
- Banner automatic refresh ad loads are now tied to Helium impression events.

This version of the Helium SDK includes support for the following Ad Providers:

>- *AdColony: 4.7.0*
>- *AdMob: 20.6.0*
>- *AppLovin: 11.3.1*
>- *Chartboost: 8.3.1*
>- *Facebook Audience Network: 6.8.0*
>- *Fyber Marketplace: 8.1.3*
>- *Google bidding: 20.6.0*
>- *InMobi: 10.0.5*
>- *ironSource: 7.2.1*
>- *Mintegral: 16.0.31*
>- *TapJoy: 12.9.1*
>- *UnityAds: 4.2.1*
>- *Vungle: 6.10.5*
>- *Yahoo: 1.14.0*

### Version 2.11.0 *(2022-07-07)*
Improvements:
- Improved Keyword targeting support.

Bug Fixes:
- Stop sending JSON `null` values in ILRD, winning bid info, and rewarded callback payloads.

This version of the Helium SDK includes support for the following Ad Providers:

>- *AdColony: 4.7.0*
>- *AdMob: 20.6.0*
>- *AppLovin: 11.3.1*
>- *Chartboost: 8.3.1*
>- *Facebook Audience Network: 6.8.0*
>- *Fyber Marketplace: 8.1.3*
>- *InMobi: 10.0.5*
>- *ironSource: 7.2.1*
>- *Mintegral: 16.0.31*
>- *TapJoy: 12.9.1*
>- *UnityAds: 4.0.1*
>- *Vungle: 6.10.5*
>- *Yahoo: 1.14.0*

### Version 2.10.0 *(2022-05-19)*
Improvements:
- Added `setGameEngine(String name, String version)` to HeliumSdk to facilitate sending game engine information for Reserved Keywords Targeting.
- All ad listeners now provide their callbacks on the main thread. It is now possible to directly call methods on Views in ad callbacks.

This version of the Helium SDK includes support for the following Ad Providers:

>- *AdColony: 4.7.0*
>- *AdMob: 20.6.0*
>- *AppLovin: 11.3.1*
>- *Chartboost: 8.3.1*
>- *Facebook Audience Network: 6.8.0*
>- *Fyber Marketplace: 8.1.3*
>- *InMobi: 10.0.5*
>- *ironSource: 7.2.1*
>- *Mintegral: 16.0.31*
>- *TapJoy: 12.9.1*
>- *UnityAds: 4.0.1*
>- *Vungle: 6.10.5*
>- *Yahoo: 1.14.0*

### Version 2.9.0 *(2022-04-21)*
Improvements:
- Added support for sending keywords to all `HeliumAd` subclasses.
- Added `didClick()` to all ad listeners.
- Moved Helium to a private maven repository. Please include `maven { url 'https://cboost.jfrog.io/artifactory/helium' }` in the repositories section of your `build.gradle`.

This version of the Helium SDK includes support for the following Ad Providers:

>- *AdColony: 4.7.0*
>- *AdMob: 20.6.0*
>- *AppLovin: 11.3.1*
>- *Chartboost: 8.3.1*
>- *Facebook Audience Network: 6.8.0*
>- *Fyber Marketplace: 8.1.3*
>- *InMobi: 10.0.5*
>- *ironSource: 7.2.1*
>- *Mintegral: 16.0.31*
>- *TapJoy: 12.9.1*
>- *UnityAds: 4.0.1*
>- *Vungle: 6.10.5*
>- *Yahoo: 1.14.0*

### Version 2.8.0 *(2022-03-24)*
Improvements:
- Added Yahoo mediation support.
- Banner auto refresh no longer fires `didCache()`, `didReceiveWinningBid()`, and `didShow()`.
- Banner gravity is now set to `CENTER`.
- Banner containers are now transparent.
- AdMob no longer requires an Activity on initialization and load and for banners on show.

Bug Fixes:
- ironSource adapter handles multiple loads better.

This version of the Helium SDK includes support for the following Ad Providers:
>- *AdColony: 4.7.0*
>- *AdMob: 20.6.0*
>- *AppLovin: 11.2.2*
>- *Chartboost: 8.3.1*
>- *Facebook Audience Network: 6.8.0*
>- *Fyber Marketplace: 8.1.3*
>- *InMobi: 10.0.3*
>- *ironSource: 7.2.1*
>- *Mintegral: 16.0.21*
>- *TapJoy: 12.9.1*
>- *UnityAds: 3.7.5*
>- *Vungle: 6.10.3*
>- *Yahoo: 1.14.0*

### Version 2.7.1 *(2022-03-08)*
Bug Fixes:
- Changing the visibility of a banner before show no longer affects auto refresh.

### Version 2.7.0 *(2022-03-03)*
Improvements:
- Added support for Android 12L (API 32).

Bug Fixes:
- Not visible banners no longer autorefresh.
- `clearLoaded()` on a showing banner no longer breaks that banner permanently.

This version of the Helium SDK includes support for the following Ad Providers:
>- *Chartboost: 8.3.1*
>- *Tapjoy: 12.9.0*
>- *Facebook Audience Network: 6.8.0*
>- *AdColony: 4.6.5*
>- *AdMob: 20.5.0*
>- *Vungle: 6.10.3*
>- *AppLovin: 11.1.2*
>- *UnityAds: 3.7.5*
>- *ironSource: 7.2.0*
>- *Fyber Marketplace: 8.1.2*
>- *InMobi: 10.0.3*
>- *Mintegral: 16.0.11*

### Version 2.6.0 *(2022-02-15)*
Improvements:
- Impression Level Revenue Data support.
- Rewarded callback support.
- Reintroduced `partner_id` to the winning bid information.
- Added COPPA settings on `HeliumSdk`.

Bug Fixes:
- Updated the US privacy string to `-` for LSPA for Fyber and Tapjoy.
- Resolved a regression in 2.5.1 that lead to the Unity consent prompt to show. The Unity adapter will use the publisher provided consent.

This version of the Helium SDK includes support for the following Ad Providers:
>- *Chartboost: 8.3.1*
>- *Tapjoy: 12.9.0*
>- *Facebook Audience Network: 6.8.0*
>- *AdColony: 4.6.5*
>- *AdMob: 20.5.0*
>- *Vungle: 6.10.3*
>- *AppLovin: 11.1.2*
>- *UnityAds: 3.7.5*
>- *ironSource: 7.1.14*
>- *Fyber Marketplace: 8.1.2*
>- *InMobi: 10.0.3*
>- *Mintegral: 16.0.11*

### Version 2.5.1 *(2022-02-03)*
Improvements:
- Updated GDPR and CCPA handling for all networks.

### Version 2.5.0 *(2022-01-14)*
Improvements:
- Banner Support*
- Mintegral Header Bidding support.
- Added Fyber, InMobi, & Mintegral mediated support.
- Updated Partner SDK Dependencies.
- Various improvements and fixes.

>*Banner Support is currently supported for the following Ad Providers:
>- Chartboost
>- Facebook Audience Network
>- AdColony
>- AdMob
>- Vungle
>- AppLovin
>- Unity Ads
>- Fyber
>- InMobi
>- Mintegral

This version of the Helium SDK includes support for the following Ad Providers:
>- *Chartboost: 8.3.0*
>- *Tapjoy: 12.8.1*
>- *Facebook Audience Network: 6.8.0*
>- *AdColony: 4.6.5*
>- *AdMob: 20.5.0*
>- *Vungle: 6.10.3*
>- *AppLovin: 10.3.5*
>- *UnityAds: 3.7.5*
>- *ironSource: 7.1.12.2*
>- *Fyber Marketplace: 8.1.0*
>- *InMobi: 10.0.1*
>- *Mintegral: 15.7.21*

### Version 2.3.2 *(2021-12-08)*
Improvements:
- Vungle Support for 6.10.1 & newer.
- Updated Partner SDK Dependencies.

This version of the Helium SDK includes support for the following Ad Providers:
>- *Chartboost: 8.2.1*
>- *Tapjoy: 12.8.1*
>- *Facebook Audience Network: 6.8.0*
>- *AdColony: 4.6.5*
>- *AdMob: 20.4.0*
>- *Vungle: 6.10.2*
>- *AppLovin: 10.3.5*
>- *UnityAds: 3.7.5*
>- *ironSource: 7.1.12.1*

### Version 2.3.1 *(2021-06-24)*
Improvements:
- Vungle Header Bidding Support.
- Updated Partner SDK Dependencies.
- Helium Adapters will now follow a `d.d.d.d` version format.

This version of the Helium SDK includes support for the following Ad Providers:
>- *Chartboost: 8.2.1*
>- *Tapjoy: 12.8.1*
>- *Facebook Audience Network: 6.5.0*
>- *AdColony: 4.5.0*
>- *AdMob: 19.7.0*
>- *Vungle: 6.9.1*
>- *AppLovin: 10.3.1*
>- *UnityAds: 3.7.2*
>- *ironSource: 7.1.5.1*

### Version 2.3.0 *(2021-02-18)*
Improvements:
- Vungle Header Bidding Support.
- Updated Partner SDK Dependencies.

This version of the Helium SDK includes support for the following Ad Providers:
>- *Chartboost: 8.2.0*
>- *Tapjoy: 12.7.1*
>- *Facebook Audience Network: 6.2.0*
>- *AdColony: 4.4.1*
>- *AdMob: 19.7.0*
>- *Vungle: 6.9.1*
>- *AppLovin: 9.15.0*
>- *UnityAds: 3.6.0*
>- *ironSource: 7.1.0.1*

### Version 2.2.1 *(2020-12-18)*
Improvements:
- Chartboost SDK 8.2.0 support.
- Updated Partner SDK Dependencies.
- Updated UnityAds Adapter.

This Helium SDK version supports the following Ad Networks:
>- *Chartboost: 8.2.0*
>- *Tapjoy: 12.7.1*
>- *Facebook Audience Network: 6.2.0*
>- *AdColony: 4.3.1*
>- *AdMob: 19.6.0*
>- *Vungle: 6.8.1*
>- *AppLovin: 9.14.8*
>- *UnityAds: 3.6.0*
>- *ironSource: 7.0.4.1*

### Version 2.2.0 *(2020-11-20)*
Improvements:
- New clearLoaded API method.
- Various improvements and fixes.

This Helium SDK version supports the following Ad Networks:
>- *Chartboost: 8.1.0*
>- *Tapjoy: 12.7.1*
>- *Facebook Audience Network: 6.2.0*
>- *AdColony: 4.3.0*
>- *AdMob: 19.5.0*
>- *Vungle: 6.8.1*
>- *AppLovin: 9.14.6*
>- *UnityAds: 3.5.0*
>- *ironSource: 7.0.3.1*

### Version 2.1.0 *(2020-10-02)*
- Added UnityAds and IronSource support.
- Various improvements and fixes.

This Helium SDK version supports the following Ad Networks:
>- *Chartboost: 8.1.0*
>- *Tapjoy: 12.6.1*
>- *Facebook Audience Network: 5.11.0*
>- *AdColony: 4.2.2*
>- *AdMob: 19.4.0*
>- *Vungle: 6.7.0*
>- *AppLovin: 9.13.4*
>- *UnityAds: 3.4.6*
>- *ironSource: 7.0.1.1*

### Version 2.0.4 *(2020-08-24)*
- Added CCPA, GDPR and User Consent setters.
- Fixed a bug where, after a failed ad load, a placement would become unusable.
- Fixed an issue where Tapjoy would fail to initialize.
- Fixed an issue where Admob would fail to load ads.
- Improved logging for easier debugging and more informative output.

This Helium SDK version supports the following Ad Networks:
>- *Chartboost: 8.1.0*
>- *Tapjoy: 12.6.1*
>- *Facebook Audience Network: 5.10.1*
>- *AdColony: 4.2.2*
>- *AdMob: 19.4.0*
>- *Vungle: 6.7.0*
>- *AppLovin: 9.13.4*

### Version 2.0.0 *(2020-05-15)*
Improvements:
- Application Context needs to be passed in the Helium SDK:
  >- Helium SDK no longer requires the inclusion of the lifecycle callbacks.

- SDKs (except for Chartboost) now require their own separate Helium Adapter. See integration instructions.

This Helium SDK version supports the following Ad Networks:
>- *Chartboost: 8.0.1+*
>- *Tapjoy: 12.4.2+*
>- *Facebook Audience Network: 5.7.1+*
>- *AdColony: 4.1.4+*
>- *AdMob: 19.0.0+*
>- *Vungle: 6.5.2+*
>- *AppLovin: 9.11.5+*


### Version 1.1.0 *(2019-10-29)*
Improvements:
- Added Support for AdColony (see integration docs).
- Added Test Mode for assisting during integration.
- Added Bintray support (see integration docs).

>- *Chartboost: 7.5.1+*
>- *TapJoy 12.2.0+*
>- *Facebook Audience Network: 5.3+*
>- *AdColony: 3.3.11+*

### Version 1.0.0 *(2019-08-15)*
Improvements:
- Support for Chartboost.
- Support for Tapjoy.
- Support for Facebook Audience Network.
- Support for Interstitial Ads.
- Support for Rewarded Ads.
- Winning bid information.

This Helium SDK version supports the following Ad Networks:
>- *Chartboost: 7.5.0+*
>- *TapJoy 12.2.0+*
>- *Facebook Audience Network: 5.3+*
