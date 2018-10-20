package com.chartboost.helium.helium_domain.ad;

import com.chartboost.helium.helium_domain.PlacementId;

public final class InterstitialLoadRequested {
    private final PlacementId placementId_;

    private InterstitialLoadRequested(PlacementId placementId) {
        placementId_ = placementId;
    }
    public static InterstitialLoadRequested create(PlacementId placementId) {
        return new InterstitialLoadRequested(placementId);
    }
}
