package com.chartboost.helium.helium_common.specification;

public interface Specification<TypeT> {
    boolean isSatisfiedBy(TypeT thisCandidate);
}
