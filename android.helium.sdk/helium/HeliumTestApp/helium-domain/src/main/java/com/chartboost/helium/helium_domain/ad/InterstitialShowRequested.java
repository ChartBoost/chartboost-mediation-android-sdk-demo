package com.chartboost.helium.helium_domain.ad;

import com.chartboost.helium.helium_common.event.Event;
import com.chartboost.helium.helium_common.event.EventType;
import com.chartboost.helium.helium_common.value.time.DKDateTimeValue;
import com.chartboost.helium.helium_common.version.QVersion;
import com.chartboost.helium.helium_domain.PlacementId;

public class InterstitialShowRequested implements Event {

    private InterstitialShowRequested() {

    }
    public static Event of(PlacementId placementIdentifier_) {
        return new InterstitialShowRequested();
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
