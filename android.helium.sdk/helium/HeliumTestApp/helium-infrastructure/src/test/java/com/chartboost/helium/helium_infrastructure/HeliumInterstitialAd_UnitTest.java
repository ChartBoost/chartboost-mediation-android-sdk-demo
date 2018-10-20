package com.chartboost.helium.helium_infrastructure;
import static org.mockito.Mockito.*;
import com.chartboost.helium.helium_common.event.EventBus;
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

    @Test
    public void interstitialAd_GetProper() {
        EventBus eventBus = null;
        HeliumInterstitialAd interstitialAd = new HeliumInterstitialAdImpl.Builder()
            .setEventBus(null)
            .setPlacementId(null)
            .setRepoFactory(null)
            .build();
        assertTrue(interstitialAd != null);
    }
}
