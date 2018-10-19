package com.chartboost.helium.helium_common.event;

import com.chartboost.helium.helium_common.identity.Identifier;
import com.chartboost.helium.helium_common.value.string.StringValue;

public class StoredEventIdentifier implements Identifier<StringValue> {
    @Override
    public StringValue id() {
        return null;
    }

    @Override
    public boolean is(StringValue type) {
        return equals(type);
    }
}
