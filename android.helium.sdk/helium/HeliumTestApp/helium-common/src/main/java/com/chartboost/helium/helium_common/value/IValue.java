package com.chartboost.helium.helium_common.value;

public interface IValue<TypeT extends IValue<TypeT>> {
    boolean equals(Object rhs);
    int hashCode();
    boolean is(TypeT type);
}