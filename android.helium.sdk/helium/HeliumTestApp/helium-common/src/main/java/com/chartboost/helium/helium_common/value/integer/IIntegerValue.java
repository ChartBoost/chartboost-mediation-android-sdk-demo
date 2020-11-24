package com.chartboost.helium.helium_common.value.integer;

import com.chartboost.helium.helium_common.value.IValue;

interface IIntegerValue<IntegerT extends IIntegerValue<IntegerT>> extends IValue<IntegerT> {
    Integer asInteger();
    String asString();
    Integer itsValue();
}