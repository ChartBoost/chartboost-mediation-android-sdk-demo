package com.chartboost.helium.helium_common.value.time;

import com.chartboost.helium.helium_common.value.IValue;

import java.time.LocalDateTime;

public interface IDateTimeValue<DateT extends IDateTimeValue<DateT>> extends IValue<DateT> {
    LocalDateTime asLocalDateTime();
    String asString();
    LocalDateTime itsValue();
}