package com.chartboost.helium.helium_common.bus;

import com.chartboost.helium.helium_common.event.EventBus;

public class CanBeAnyThread implements RunningThreadSpecifications{
    @Override
    public void mustBeSpecifiedThread(EventBus eventBus) {
        //we dont care which thread we are executing on, so dont trigger stack-unwinding
    }
}
