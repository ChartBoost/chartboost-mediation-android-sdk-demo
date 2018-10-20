package com.chartboost.helium.helium_infrastructure;

import com.chartboost.helium.helium_infrastructure.internal.HeliumInterstitialAdImpl;

import org.junit.Test;
import static org.junit.Assert.*;

public class HeliumInterstitialAd_UnitTest {
    @Test
    public void interstitialAd_Get() {
        HeliumInterstitialAd interstitialAd = new HeliumInterstitialAdImpl.Builder()
            .setEventBus(null)
            .setPlacementId(null)
            .setRepoFactory(null)
            .build();
        assertTrue(interstitialAd != null);
    }
}
