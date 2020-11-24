package com.chartboost.helium.helium_common.value.time;

import java.time.LocalDateTime;

public class DKDateTimeValue implements IDateTimeValue<DKDateTimeValue> {
    private LocalDateTime localDateTime_;

    private DKDateTimeValue() {
        localDateTime_ = LocalDateTime.now();
    }

    public static DKDateTimeValue now() {
        DKDateTimeValue d = new DKDateTimeValue();
        return d;
    }
    @Override
    public LocalDateTime asLocalDateTime() {
        return localDateTime_;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public LocalDateTime itsValue() {
        return null;
    }

    @Override
    public boolean is(DKDateTimeValue type) {
        return false;
    }
}
