package com.chartboost.helium.helium_infrastructure.internal;

import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAd;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAdDelegate;

public class HeliumAdContext {
    private HeliumAdContextState state_;
    private final HeliumInterstitialAdDelegate delegate_;
    private final Object callee_;
    private HeliumAdContext(final HeliumInterstitialAdDelegate delegate, final Object callee) {
        state_ = HeliumAdContextState.HeliumAdContextState_Initial;
        delegate_ = delegate;
        callee_ = callee;
    }
    public static HeliumAdContext of(final HeliumInterstitialAdDelegate delegate, final Object callee) {
        return new HeliumAdContext(delegate, callee);
    }
    public void yourAreLoading() {
        state_ = HeliumAdContextState.HeliumAdContextState_Loading;
    }
    public void yourAreShowing() {
        state_ = HeliumAdContextState.HeliumAdContextState_Showing;
    }
    public void yourAreLoaded() {
        state_ = HeliumAdContextState.HeliumAdContextState_Loaded;
    }
    public void yourAreShown() {
        state_ = HeliumAdContextState.HeliumAdContextState_Shown;
    }
    public void notifyCallerWithError(int error) {
        switch (state_) {
            case HeliumAdContextState_Max: {

            } break;
            case HeliumAdContextState_Shown: {
                if (delegate_ != null) {
                    delegate_.interstitialAdDidShow((HeliumInterstitialAd)callee_, null); //todo - error handling
                }
            } break;
            case HeliumAdContextState_Loaded: {
                if (delegate_ != null) {
                    delegate_.interstitialAdDidLoad((HeliumInterstitialAd)callee_, null); //todo - error handling
                }
            } break;
            case HeliumAdContextState_Initial: {

            } break;
            case HeliumAdContextState_Loading: {

            } break;
            case HeliumAdContextState_Showing: {

            } break;
        }
    }
}
