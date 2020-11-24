package com.chartboost.helium.helium_domain.ad;


enum Unit {
    Unit_Milliseconds,
    Unit_Seconds
};

public class TimeUnit {
    private int time_;
    private Unit unit_;

    private TimeUnit(int time, Unit unit) {
        time_ = time;
        unit_ = unit;
    }

    public static TimeUnit timeUnit(int time, Unit unit) {
        return new TimeUnit(time, unit);
    }

    public int asSeconds() {
        int r = time_;
        if (unit_ == Unit.Unit_Milliseconds) {
            r /= 1000;
        }
        return r;
    }

    public int asMilliSeconds() {
        int r = time_;
        if (unit_ == Unit.Unit_Seconds) {
            r *= 1000;
        }
        return r;
    }
}
