package com.chartboost.helium.helium_common.value.bool;

import com.chartboost.helium.helium_common.value.IValue;

interface IBooleanValue<BooleanT extends IBooleanValue<BooleanT>> extends IValue<BooleanT> {
    BooleanT asBoolean();
    String asString();
    BooleanT itsValue();
}