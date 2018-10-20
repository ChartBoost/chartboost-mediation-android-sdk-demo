package com.chartboost.helium.helium_domain.ad;

import com.chartboost.helium.helium_common.event.Event;
import com.chartboost.helium.helium_common.event.EventType;
import com.chartboost.helium.helium_common.value.time.DKDateTimeValue;
import com.chartboost.helium.helium_common.version.QVersion;
import com.chartboost.helium.helium_domain.PlacementId;

public final class InterstitialLoadRequested implements Event {
    private final PlacementId placementId_;

    private InterstitialLoadRequested(PlacementId placementId) {
        placementId_ = placementId;
    }
    public static InterstitialLoadRequested of(PlacementId placementId) {
        return new InterstitialLoadRequested(placementId);
    }

    @Override
    public Object payload() {
        return null;
    }

    @Override
    public EventType eventType() {
        return null;
    }

    @Override
    public QVersion version() {
        return null;
    }

    @Override
    public DKDateTimeValue occuredOn() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }
}
