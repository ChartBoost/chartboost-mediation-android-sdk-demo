package com.chartboost.helium.helium_common.specification;


import java.util.HashSet;
import java.util.Set;

public abstract class CompositeSpecification<T> implements Specification<T> {
    protected final Set<Specification<T>> operands_;
    protected CompositeSpecification(Specification<T> lhs, Specification<T> rhs) {
        operands_ = new HashSet<>();
        operands_.add(lhs);
        operands_.add(rhs);
    }
}
