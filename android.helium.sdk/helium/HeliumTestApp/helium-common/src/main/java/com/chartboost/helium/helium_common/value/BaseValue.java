package com.chartboost.helium.helium_common.value;

public class BaseValue<TypeT extends BaseValue<TypeT, T>, T> implements IValue<TypeT> {
    protected T value_;

    protected BaseValue() {}
    protected BaseValue(T value) {
        value_ = value;
    }


    @Override
    public boolean is(TypeT type) {
        return false; //isSameAs type?
    }

    @Override
    public boolean equals(Object other) {
        return ((other instanceof BaseValue) && (is((TypeT) other)));
    }

    @Override
    public int hashCode() {
return 11;
    }
}