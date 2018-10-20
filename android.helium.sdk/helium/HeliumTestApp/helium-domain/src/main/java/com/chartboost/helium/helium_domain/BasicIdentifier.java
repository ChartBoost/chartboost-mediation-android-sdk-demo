package com.chartboost.helium.helium_domain;

import com.chartboost.helium.helium_common.identity.Identifier;
import com.chartboost.helium.helium_common.value.string.StringValue;

import java.util.UUID;

public final class BasicIdentifier implements Identifier<StringValue> {
    private final StringValue identifier_;

    public BasicIdentifier() {
        identifier_ = StringValue.valueOf(UUID.randomUUID().toString());
    }

    @Override
    public StringValue id() {
        return identifier_;
    }

    @Override
    public boolean is(StringValue type) {
        return equals(type);
    }

    @Override
    public int hashCode() {
        return identifier_ != null ? identifier_.hashCode() : 0;
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }
        if (!(rhs instanceof BasicIdentifier)) {
            return false;
        }
        BasicIdentifier that = (BasicIdentifier)rhs; //safe
        if (this.identifier_ == that.identifier_) {
            return true;
        }
        if (this.identifier_ == null) {
            return false;
        }
        return identifier_.equals(rhs);
    }

}
