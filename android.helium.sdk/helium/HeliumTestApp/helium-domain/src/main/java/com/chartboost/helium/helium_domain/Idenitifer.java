package com.chartboost.helium.helium_domain;

import java.util.UUID;

public final class Idenitifer {
    private final String identifier_;

    public Idenitifer() {
        identifier_ = UUID.randomUUID().toString();
    }

    public String getIdentifier() {
        return identifier_;
    }

    //todo: override below 2 methods
    //equal
    //hash

}
