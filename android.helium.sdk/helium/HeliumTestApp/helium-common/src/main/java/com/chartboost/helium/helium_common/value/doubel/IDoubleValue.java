package com.chartboost.helium.helium_common.value.doubel;

import com.chartboost.helium.helium_common.value.IValue;

interface IDoubleValue<DoubleT extends IDoubleValue<DoubleT>> extends IValue<DoubleT> {
    Double asDouble();
    String asString();
    Double itsValue();
}