package com.chartboost.helium.helium_interactors.repos;

public interface CompletionCallback<AggregateT> {
    void didGet(AggregateT aggregate);
}
