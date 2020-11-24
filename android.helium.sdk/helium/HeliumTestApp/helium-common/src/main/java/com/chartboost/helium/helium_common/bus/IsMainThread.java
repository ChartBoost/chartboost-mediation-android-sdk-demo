package com.chartboost.helium.helium_common.bus;

import android.os.Looper;

import com.chartboost.helium.helium_common.event.EventBus;

public class IsMainThread implements RunningThreadSpecifications{
    private RunningThreadSpecifications main_;

    public RunningThreadSpecifications mainThreadSpecs() {
        main_ = (EventBus bus) -> {
            Looper expectedToBeMain = Looper.myLooper();
            Looper main = Looper.getMainLooper();
            if (expectedToBeMain != main) {
                onAssertionFailure();
                throwIllegalStateException();
            }
        };
        return main_;
    }

    private void throwIllegalStateException() {
        throw new IllegalThreadStateException("Expected this function to be executed in the context of main thread!");
    }

    @Override
    public void mustBeSpecifiedThread(EventBus eventBus) {
        RunningThreadSpecifications mainthread = mainThreadSpecs();
        mainthread.mustBeSpecifiedThread(eventBus);
    }

    protected void onAssertionFailure() {
        //DomainLogger.logFailure("Expected this function to be executed in the context of main thread!");
    }
}
