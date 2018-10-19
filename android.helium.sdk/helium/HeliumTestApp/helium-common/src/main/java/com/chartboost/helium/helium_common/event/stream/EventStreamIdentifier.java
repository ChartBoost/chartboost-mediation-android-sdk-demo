package com.chartboost.helium.helium_common.event.stream;

import com.chartboost.helium.helium_common.identity.Identifier;
import com.chartboost.helium.helium_common.value.string.StringValue;

public class EventStreamIdentifier implements Identifier<StringValue> {

    @Override
    public StringValue id() {
        return null;
    }

    @Override
    public boolean is(StringValue type) {
        return false;
    }
}
