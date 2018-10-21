package com.chartboost.helium.helium_interactors.repos;

import com.chartboost.helium.helium_domain.BasicIdentifier;
import com.chartboost.helium.helium_domain.bid.BidAggregate;

public class BidRepo implements Repo<BidAggregate> {
    @Override
    public void get(BasicIdentifier identifier, CompletionCallback<BidAggregate> completion) {

    }

    @Override
    public void put(BasicIdentifier identifier, CompletionCallback<BidAggregate> completion) {

    }

    @Override
    public String name() {
        return null;
    }
}
