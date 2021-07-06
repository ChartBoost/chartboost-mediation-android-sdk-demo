Android Change Log
==================
Check for the latest Helium SDK at the Helium website.

### Version 2.3.1 *(6-24-2021)*
----------------------------
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

### Version 2.2.1 *(12-18-2020)*
----------------------------
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

### Version 2.2.0 *(11-20-2020)*
--------------------------------
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
--------------------------------
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

### Version 2.0.4 *(2020-8-24)*
--------------------------------
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

### Version 2.0.0 *(2020-5-15)*
--------------------------------
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
--------------------------------
Improvements:
- Added Support for AdColony (see integration docs).
- Added Test Mode for assisting during integration.
- Added Bintray support (see integration docs).

>- *Chartboost: 7.5.1+*
>- *TapJoy 12.2.0+*
>- *Facebook Audience Network: 5.3+*
>- *AdColony: 3.3.11+*

### Version 1.0.0 *(2019-8-15)*
--------------------------------
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