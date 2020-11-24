package com.chartboost.helium.helium_interactors.repos;

import com.chartboost.helium.helium_domain.BasicIdentifier;
import com.chartboost.helium.helium_domain.ad.AdAggregate;

public class AdRepo implements Repo<AdAggregate> {

    @Override
    public void get(BasicIdentifier identifier, CompletionCallback<AdAggregate> completion) {

    }

    @Override
    public void put(BasicIdentifier identifier, CompletionCallback<AdAggregate> completion) {

    }

    @Override
    public String name() {
        return null;
    }
}
