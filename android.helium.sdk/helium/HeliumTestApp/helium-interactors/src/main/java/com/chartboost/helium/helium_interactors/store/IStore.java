package com.chartboost.helium.helium_interactors.store;

import com.chartboost.helium.helium_domain.BasicIdentifier;



interface IStoreStatus<AggregateT>  {
    void handle(StoreResult<AggregateT> result);
}

public interface IStore<AggregateT> {
    void read(BasicIdentifier identifier, IStoreStatus<AggregateT> completion);
    void write(AggregateT domainObject, IStoreStatus<AggregateT> completion);
    void delete(BasicIdentifier identifier, IStoreStatus<AggregateT> completion);
    String name();

}
