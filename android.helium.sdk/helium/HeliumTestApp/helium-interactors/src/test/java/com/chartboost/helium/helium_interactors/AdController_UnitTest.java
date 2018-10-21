package com.chartboost.helium.helium_interactors;

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
}
