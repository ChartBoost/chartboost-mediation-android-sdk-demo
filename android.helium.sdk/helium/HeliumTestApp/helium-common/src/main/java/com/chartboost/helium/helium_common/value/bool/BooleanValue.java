package com.chartboost.helium.helium_common.value.bool;

public class BooleanValue implements IBooleanValue<BooleanValue> {
    private Boolean pointer_;
    private BooleanValue(boolean value) {
        pointer_ = Boolean.valueOf(value);
    }
    public static BooleanValue valueOf(boolean value) {
        return new BooleanValue(value);
    }
    @Override
    public BooleanValue asBoolean() {
        if (pointer_ != null) {
            return BooleanValue.valueOf(pointer_.booleanValue());
        }
        return null;
    }

    @Override
    public String asString() {
        if (pointer_ != null) {
            return pointer_.toString();
        }
        return null;
    }

    @Override
    public BooleanValue itsValue() {
        return asBoolean();
    }

    @Override
    public boolean is(BooleanValue type) {
        return equals(type);
    }
}
