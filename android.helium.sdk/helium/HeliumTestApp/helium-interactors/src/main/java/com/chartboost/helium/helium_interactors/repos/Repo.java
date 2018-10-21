package com.chartboost.helium.helium_interactors.repos;

import com.chartboost.helium.helium_domain.BasicIdentifier;

public interface Repo<AggregateT> {

    void get(BasicIdentifier identifier, CompletionCallback<AggregateT> completion);
    void put(BasicIdentifier identifier, CompletionCallback<AggregateT> completion);
    String name();

}
