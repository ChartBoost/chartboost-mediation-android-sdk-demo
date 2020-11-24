package com.chartboost.helium.helium_domain;


public abstract class BasicAggregate implements Aggregate {
    @Override
    public String version() {
        return "1.0.0";
    }
}
