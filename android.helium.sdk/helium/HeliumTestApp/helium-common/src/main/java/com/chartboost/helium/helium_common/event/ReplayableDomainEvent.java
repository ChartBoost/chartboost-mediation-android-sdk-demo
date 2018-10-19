package com.chartboost.helium.helium_common.event;

import com.chartboost.helium.helium_common.value.lawng.LongValue;

public class ReplayableDomainEvent {
    private DomainEvent pastDomainEvent;
    private LongValue identifier_;
}
