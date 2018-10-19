package com.chartboost.helium.helium_interactors.store.cloud;

import com.chartboost.helium.helium_common.OnError;
import com.chartboost.helium.helium_common.OnSuccess;

public interface IApiClient<AggregateT> {
    void fetchAnyAd(OnSuccess<AggregateT> successCallback, OnError errorCallback); //gets bid response after running auction on prebid server
}
