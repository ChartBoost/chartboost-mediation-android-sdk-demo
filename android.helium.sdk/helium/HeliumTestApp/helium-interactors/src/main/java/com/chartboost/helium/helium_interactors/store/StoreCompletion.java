package com.chartboost.helium.helium_interactors.store;

import com.chartboost.helium.helium_common.OnError;
import com.chartboost.helium.helium_common.OnSuccess;

public final class StoreCompletion<AggregateT> {
    private final OnSuccess<AggregateT> successCallback_;
    private final OnError errorCallback_;
    private final AggregateT aggregateT_;
    final Throwable error_;

    private StoreCompletion(final AggregateT aggregateT,
                            final Throwable error,
                            final OnSuccess<AggregateT> successCallback,
                            final OnError errorCallback) {
        successCallback_ = successCallback;
        errorCallback_ = errorCallback;
        aggregateT_ = aggregateT;
        error_ = error;
    }

    public static StoreCompletion of(final Object aggregateT,
                                     final Throwable error,
                                     final OnSuccess successCallback,
                                     final OnError errorCallback) {
        return new StoreCompletion(aggregateT, error, successCallback, errorCallback);
    }
    
    public void runCompletion() {
        if (successCallback_ != null && aggregateT_ != null) {
            successCallback_.handle(aggregateT_);
            return;
        }

        if (errorCallback_ != null && error_ != null) {
            errorCallback_.handle(error_);
        }
    }
}
