package com.chartboost.helium.helium_common.value.lawng;

import com.chartboost.helium.helium_common.value.IValue;

public interface ILongValue<LongT extends ILongValue<LongT>> extends IValue<LongT> {
    Long asLong();
    String asString();
    Long itsValue();
}