package com.chartboost.helium.helium_interactors.store.cloud;

public interface IApiClient<AggregateT extends AbstractAggregate> {
    void fetchAnyAd(OnSuccess<AggregateT> successCallback, OnError errorCallback); //gets bid response after running auction on prebid server
}
