package com.chartboost.helium.helium_domain.ad;

public class AdExpiryTimer {
    private TimeUnit timeUnit_;

    private AdExpiryTimer(TimeUnit timeUnit) {
        timeUnit_ = timeUnit;
    }

    public static AdExpiryTimer adExpiryTimer(TimeUnit timeUnit) {
        return new AdExpiryTimer(timeUnit);
    }

    public void invalidate() {

    }

    public void start() {

    }
}
