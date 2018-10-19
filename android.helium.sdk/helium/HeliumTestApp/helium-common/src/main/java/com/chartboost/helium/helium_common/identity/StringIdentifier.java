package com.chartboost.helium.helium_common.identity;

import com.chartboost.helium.helium_common.value.string.StringValue;

public final class StringIdentifier implements Identifier<StringValue> {
    private final StringValue value_;

    private StringIdentifier() {
        this("");
    }
    private StringIdentifier(String str) {
        value_ = StringValue.valueOf(str);
    }


    public static StringIdentifier empty() {
        StringIdentifier d = new StringIdentifier();
        return d;
    }

    public static StringIdentifier from(String strValue) {
        StringIdentifier d = new StringIdentifier(strValue);
        return d;
    }

    @Override
    public StringValue id() {
        return value_;
    }

    @Override
    public String toString() {
        return value_.asString();
    }

    @Override
    public boolean is(StringValue type) {//TODO
        return this.equals(type) && value_.is(type); //TODO: Value equality
    }

}
