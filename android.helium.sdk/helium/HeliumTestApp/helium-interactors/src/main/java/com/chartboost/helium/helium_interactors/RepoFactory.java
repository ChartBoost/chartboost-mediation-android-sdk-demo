package com.chartboost.helium.helium_interactors;

import com.chartboost.helium.helium_domain.BasicIdentifier;
import com.chartboost.helium.helium_domain.ad.AdAggregate;
import com.chartboost.helium.helium_interactors.repos.GetAdCompletion;

public class RepoFactory {
    private RepoFactory() {}

    public static RepoFactory of() {
        return new RepoFactory();
    }

    public void getAd(BasicIdentifier adid, GetAdCompletion completion) {
        AdAggregate adAggregate = null;

        adAggregate = new AdAggregate();

        completion.didGetAd(adAggregate);
    }
}
