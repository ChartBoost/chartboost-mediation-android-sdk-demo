package com.chartboost.helium.helium_interactors.repos;

import com.chartboost.helium.helium_domain.ad.AdAggregate;

public interface GetAdCompletion {
    void didGetAd(AdAggregate adAggregate);
}
