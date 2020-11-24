package com.chartboost.helium.helium_common.identity;

import com.chartboost.helium.helium_common.value.IValue;
import com.chartboost.helium.helium_common.value.string.StringValue;

public interface Identifier<Type extends IValue<Type>> extends IValue<Type> {
    StringValue id();
}
