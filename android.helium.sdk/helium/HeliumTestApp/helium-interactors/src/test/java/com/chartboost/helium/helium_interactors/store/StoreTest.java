package com.chartboost.helium.helium_interactors.store;

import com.chartboost.helium.helium_common.OnError;
import com.chartboost.helium.helium_common.OnSuccess;
import com.chartboost.helium.helium_domain.BasicIdentifier;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


class StoreResult<AggregateT> {
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

interface IStoreStatus<AggregateT>  {
    void handle(StoreResult<AggregateT> result);
}

interface IStore<AggregateT> {
    void read(BasicIdentifier identifier, IStoreStatus<AggregateT> completion);
    void write(AggregateT domainObject, IStoreStatus<AggregateT> completion);
    void delete(BasicIdentifier identifier, IStoreStatus<AggregateT> completion);
}




class DummyAggregate {
    public int dummyVal;
}

class DummyStore implements IStore<DummyAggregate> {

    @Override
    public void read(BasicIdentifier identifier, IStoreStatus<DummyAggregate> completion) {
        if (completion != null) {
            StoreResult<DummyAggregate> storeResult = new StoreResult();
            DummyAggregate dummyAggregate = new DummyAggregate();
            storeResult.setAggregate(dummyAggregate);
            completion.handle(storeResult);
        }
    }

    @Override
    public void write(DummyAggregate domainObject, IStoreStatus<DummyAggregate> completion) {
        if (completion != null) {
            StoreResult<DummyAggregate> storeResult = new StoreResult();
            DummyAggregate dummyAggregate = new DummyAggregate();
            storeResult.setAggregate(domainObject);
            completion.handle(storeResult);
        }
    }

    @Override
    public void delete(BasicIdentifier identifier, IStoreStatus<DummyAggregate> completion) {
        if (completion != null) {
            StoreResult<DummyAggregate> storeResult = new StoreResult();
            DummyAggregate dummyAggregate = new DummyAggregate();
            storeResult.setAggregate(dummyAggregate);
            completion.handle(storeResult);
        }
    }
}

public class StoreTest {

    @Test
    public void create() {
        IStore<DummyAggregate> dummyAggregateStore = new DummyStore();
        Assert.assertTrue(dummyAggregateStore != null);
        BasicIdentifier identifier = new BasicIdentifier();
        dummyAggregateStore.read(identifier, (StoreResult<DummyAggregate> result) -> {
            if (result != null) {

            }
        });
    }

    @Test
    public void read() {
        IStore<DummyAggregate> dummyAggregateStore = new DummyStore();
        Assert.assertTrue(dummyAggregateStore != null);
        BasicIdentifier identifier = new BasicIdentifier();
        dummyAggregateStore.read(identifier, (StoreResult<DummyAggregate> result) -> {
            if (result != null) {

            }
        });
    }

    @Test
    public void write() {
        IStore<DummyAggregate> dummyAggregateStore = new DummyStore();
        Assert.assertTrue(dummyAggregateStore != null);
        DummyAggregate da = new DummyAggregate();
        dummyAggregateStore.write(da, (StoreResult<DummyAggregate> result) -> {
            if (result != null) {

            }
        });
    }

    @Test
    public void delete() {
        IStore<DummyAggregate> dummyAggregateStore = new DummyStore();
        Assert.assertTrue(dummyAggregateStore != null);
        BasicIdentifier identifier = new BasicIdentifier();
        dummyAggregateStore.delete(identifier, (StoreResult<DummyAggregate> result) -> {
            if (result != null) {

            }
        });
    }

    @Test
    public void name() {
    }
}