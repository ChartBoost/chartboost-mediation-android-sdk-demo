package com.chartboost.helium.helium_domain.service;

import com.chartboost.helium.helium_common.event.EventBus;

public class SdkRoutingDomainService implements SdkRoutingService {
    private SdkRoutingDomainService() {}

    public static SdkRoutingDomainService of() {
        return new SdkRoutingDomainService();
    }
}
