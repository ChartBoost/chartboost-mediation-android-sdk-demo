package com.chartboost.helium.helium_interactors.store;

import com.chartboost.helium.helium_domain.BasicIdentifier;

public interface Store<AggregateT> {

    void create(StoreCompletion<AggregateT> thenRunLambda);
    void read(BasicIdentifier identifier, StoreCompletion<AggregateT> thenRunLambda);
    void write(AggregateT domainObject, StoreCompletion<AggregateT> thenRunLambda);
    void deleteit(BasicIdentifier identifier, StoreCompletion<AggregateT> thenRunLambda);
    String name();

}
