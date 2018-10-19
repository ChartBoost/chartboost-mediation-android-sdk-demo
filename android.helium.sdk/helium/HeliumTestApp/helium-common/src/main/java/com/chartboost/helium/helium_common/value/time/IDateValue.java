package com.chartboost.helium.helium_common.value.time;

import com.chartboost.helium.helium_common.value.IValue;

import java.time.LocalDate;

interface IDateValue<DateT extends IDateValue<DateT>> extends IValue<DateT> {
    LocalDate asLocalDate();
    String asString();
    LocalDate itsValue();
}