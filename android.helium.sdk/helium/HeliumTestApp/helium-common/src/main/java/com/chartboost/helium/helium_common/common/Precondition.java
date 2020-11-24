package com.chartboost.helium.helium_common.common;

public class Precondition {
    public static <TypeT> TypeT isNotNull(TypeT pointer) {
        if (pointer == null) {
            throw new NullPointerException();
        }
        return pointer;
    }

    public static <TypeT> TypeT isNull(TypeT pointer) {
        if (pointer != null) {
            throw new IllegalStateException();
        }
        return pointer;
    }

    public static void isTrue(boolean b) {
        if (!b) {
            throw new IllegalStateException();
        }
    }
}
