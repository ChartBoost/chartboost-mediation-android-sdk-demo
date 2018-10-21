package com.chartboost.helium.helium_domain.ad;

import com.chartboost.helium.helium_common.event.Event;
import com.chartboost.helium.helium_common.event.EventType;
import com.chartboost.helium.helium_common.value.time.DKDateTimeValue;
import com.chartboost.helium.helium_common.version.QVersion;
import com.chartboost.helium.helium_domain.PlacementId;

public class InterstitialShowRequested implements Event {
    private PlacementId placementIdentifier_;
    private InterstitialShowRequested(PlacementId placementIdentifier) {
        placementIdentifier_ = placementIdentifier;
    }
    public static Event of(PlacementId placementIdentifier) {
        return new InterstitialShowRequested(placementIdentifier);
    }

    @Override
    public Object payload() {
        return null;
    }

    @Override
    public EventType eventType() {
        return EventType.App;
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
