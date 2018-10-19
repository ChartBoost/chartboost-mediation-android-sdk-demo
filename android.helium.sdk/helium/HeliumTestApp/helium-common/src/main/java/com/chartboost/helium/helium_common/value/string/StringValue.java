package com.chartboost.helium.helium_common.value.string;

public class StringValue implements IStringValue<StringValue> {
    private String pointer_;
    private StringValue(String pointer) {
        pointer_ = new String(pointer);
    }
    public static StringValue valueOf(String str) {
        return new StringValue(str);
    }

    @Override
    public String asString() {
        return new String(pointer_);
    }

    @Override
    public String itsValue() {
        return new String(pointer_);
    }

    @Override
    public boolean is(StringValue type) {
        return equals(type);
    }
    //TODO: Override equals and hash
}
