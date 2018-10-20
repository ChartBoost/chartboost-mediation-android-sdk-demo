package com.chartboost.helium.helium_infrastructure;
import static org.mockito.Mockito.*;
import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_infrastructure.internal.HeliumInterstitialAdImpl;
import com.chartboost.helium.helium_interactors.RepoFactory;

import org.junit.Test;
import org.mockito.Mockito;

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
        EventBus eventBus = Mockito.mock(EventBus.class);
        String p = "placementid";
        RepoFactory repoFactory = Mockito.mock(RepoFactory.class);
        HeliumInterstitialAd interstitialAd = new HeliumInterstitialAdImpl.Builder()
            .setEventBus(eventBus)
            .setPlacementId(p)
            .setRepoFactory(repoFactory)
            .build();
        assertTrue(interstitialAd != null);
    }

    @Test
    public void interstitialAd_GetPlacementId() {
        EventBus eventBus = Mockito.mock(EventBus.class);
        String p = "placementid";
        RepoFactory repoFactory = Mockito.mock(RepoFactory.class);
        HeliumInterstitialAd interstitialAd = new HeliumInterstitialAdImpl.Builder()
            .setEventBus(eventBus)
            .setPlacementId(p)
            .setRepoFactory(repoFactory)
            .build();
        assertTrue(interstitialAd != null);
        String actualPlacement = interstitialAd.placementId();
        assertTrue(actualPlacement.equalsIgnoreCase(p));
    }

    @Test
    public void interstitialAd_loadAdWithDelegate() {
        HeliumInterstitialAdDelegate adDelegate = Mockito.mock(HeliumInterstitialAdDelegate.class);
        EventBus eventBus = Mockito.mock(EventBus.class);
        String p = "placementid";
        RepoFactory repoFactory = Mockito.mock(RepoFactory.class);
        HeliumInterstitialAd interstitialAd = new HeliumInterstitialAdImpl.Builder()
            .setEventBus(eventBus)
            .setPlacementId(p)
            .setRepoFactory(repoFactory)
            .build();
        assertTrue(interstitialAd != null);
        String actualPlacement = interstitialAd.placementId();
        assertTrue(actualPlacement.equalsIgnoreCase(p));
        interstitialAd.loadAdWithDelegate(adDelegate);

        verify(adDelegate).interstitialAdDidLoad(any(), any());
    }
}
