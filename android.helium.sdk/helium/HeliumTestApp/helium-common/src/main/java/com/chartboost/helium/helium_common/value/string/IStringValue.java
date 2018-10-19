package com.chartboost.helium.helium_common.value.string;

import com.chartboost.helium.helium_common.value.IValue;

interface IStringValue<StringT extends IStringValue<StringT>> extends IValue<StringT> {
    String asString();
    String itsValue();
}