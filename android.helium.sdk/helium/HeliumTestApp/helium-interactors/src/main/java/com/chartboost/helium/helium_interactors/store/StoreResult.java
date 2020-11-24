package com.chartboost.helium.helium_interactors.store;

public class StoreResult<AggregateT> {
    private AggregateT aggregate_;
    private Throwable error_;

    public StoreResult() {
        aggregate_ = null;
        error_ = null;
    }

    public void setAggregate(AggregateT aggregate) {
        aggregate_ = aggregate;
    }

    public void setError(Throwable error) {
        error_ = error;
    }

    public AggregateT getAggregate() {
        return aggregate_;
    }

    public Throwable getError() {
        return error_;
    }
}
