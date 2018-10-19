package com.chartboost.helium.helium_common.value.uuid;

import com.chartboost.helium.helium_common.value.IValue;

import java.util.UUID;

interface IUuidValue<UuidT extends IUuidValue<UuidT>> extends IValue<UuidT> {
    UUID asUuid();
    String asString();
    UUID itsValue();
}