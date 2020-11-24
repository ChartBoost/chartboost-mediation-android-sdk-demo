package com.chartboost.helium.helium_common.version;

import com.chartboost.helium.helium_common.value.IValue;

public interface Version<T extends IValue<T>> {
    T versionAsString();
}
