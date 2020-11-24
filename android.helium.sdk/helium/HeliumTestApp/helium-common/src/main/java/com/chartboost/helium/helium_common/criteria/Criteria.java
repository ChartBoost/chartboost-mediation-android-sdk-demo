package com.chartboost.helium.helium_common.criteria;

//connect this to Predicate whenever android starts supporting Function
public interface Criteria<TypeT> {
    boolean test(TypeT itsValue);
}
