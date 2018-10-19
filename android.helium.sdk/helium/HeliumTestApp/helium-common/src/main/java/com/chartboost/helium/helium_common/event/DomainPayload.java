package com.chartboost.helium.helium_common.event;

final public class DomainPayload {
    private final Object payload_;

    private DomainPayload(Object payload) {
        payload_ = payload;
    }

    public static DomainPayload from(Object payload) {
        return new DomainPayload(payload);
    }

    public Object getPayload() {
        return payload_;
    }
}
