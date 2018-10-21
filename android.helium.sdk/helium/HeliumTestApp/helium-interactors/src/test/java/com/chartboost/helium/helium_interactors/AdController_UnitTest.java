package com.chartboost.helium.helium_interactors;

import com.chartboost.helium.helium_common.bus.HeliumVersion1EventBus;
import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_domain.service.SdkRoutingDomainService;
import com.chartboost.helium.helium_interactors.controllers.AdController;

import org.junit.Assert;
import org.junit.Test;

public class AdController_UnitTest {
    @Test
    public void adController_construct() {
        AdController adController = AdController.Builder.of()
            .setEventBus(null)
            .setRepoFactory(null)
            .setSdkRoutingDomainService(null)
            .build();
        Assert.assertTrue(adController != null);
    }

    @Test
    public void adController_constructProper() {
        EventBus eventBus = HeliumVersion1EventBus.of();
        RepoFactory repoFactory = RepoFactory.of();
        SdkRoutingDomainService routingDomainService = SdkRoutingDomainService.of();

        AdController adController = AdController.Builder.of()
            .setEventBus(eventBus)
            .setRepoFactory(repoFactory)
            .setSdkRoutingDomainService(routingDomainService)
            .build();
        Assert.assertTrue(adController != null);
    }
}
