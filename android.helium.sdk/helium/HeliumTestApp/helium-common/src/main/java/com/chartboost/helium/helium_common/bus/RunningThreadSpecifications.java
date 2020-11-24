package com.chartboost.helium.helium_common.bus;

import com.chartboost.helium.helium_common.event.EventBus;

public interface RunningThreadSpecifications {
    void mustBeSpecifiedThread(EventBus eventBus);
}
