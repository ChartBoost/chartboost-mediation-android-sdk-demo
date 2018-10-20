package com.chartboost.helium.helium_domain;

public final class PlacementId {
    private final IStringValue placementId_;

    private PlacementId(String placementId) {
        //placementId_ = new String(placementId);
    }

    public static PlacementId of(String placementId) {
        return new PlacementId(placementId);
    }

    public String placementId() {
        //return placementId_;
        return null;
    }
}
