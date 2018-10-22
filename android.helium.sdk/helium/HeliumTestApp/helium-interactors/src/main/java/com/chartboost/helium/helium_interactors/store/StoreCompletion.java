package com.chartboost.helium.helium_interactors.store;

import com.chartboost.helium.helium_common.OnError;
import com.chartboost.helium.helium_common.OnSuccess;

public class StoreCompletion<AggregateT> {
    private OnSuccess<AggregateT> successCallback_;
    private OnError errorCallback_;
}
