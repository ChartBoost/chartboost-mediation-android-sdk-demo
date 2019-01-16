package com.chartboost.helium.helium_interactors.store;

import com.chartboost.helium.helium_common.OnError;
import com.chartboost.helium.helium_common.OnSuccess;
import com.chartboost.helium.helium_domain.BasicIdentifier;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;



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

    @Override
    public String name() {
        return "Dummy";
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