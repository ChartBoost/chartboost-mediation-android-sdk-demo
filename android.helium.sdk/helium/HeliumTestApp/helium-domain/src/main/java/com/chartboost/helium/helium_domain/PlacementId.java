package com.chartboost.helium.helium_domain;

import com.chartboost.helium.helium_common.value.string.StringValue;

public final class PlacementId {
    private final StringValue placementId_;

    private PlacementId(String placementId) {
        placementId_ = StringValue.valueOf(placementId);
    }

    public static PlacementId of(String placementId) {
        return new PlacementId(placementId);
    }

    public String placementId() {
        return placementId_ == null ? null : placementId_.asString();
    }
}
