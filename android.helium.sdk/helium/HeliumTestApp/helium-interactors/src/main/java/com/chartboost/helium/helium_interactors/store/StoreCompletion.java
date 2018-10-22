package com.chartboost.helium.helium_interactors.store;

import com.chartboost.helium.helium_common.OnError;
import com.chartboost.helium.helium_common.OnSuccess;

public final class StoreCompletion<AggregateT> {
    private final OnSuccess<AggregateT> successCallback_;
    private final OnError errorCallback_;

    private StoreCompletion(OnSuccess<AggregateT> successCallback, OnError errorCallback) {
        successCallback_ = successCallback;
        errorCallback_ = errorCallback;
    }

    public static StoreCompletion of(OnSuccess successCallback, OnError errorCallback) {
        return new StoreCompletion(successCallback, errorCallback);
    }
}
