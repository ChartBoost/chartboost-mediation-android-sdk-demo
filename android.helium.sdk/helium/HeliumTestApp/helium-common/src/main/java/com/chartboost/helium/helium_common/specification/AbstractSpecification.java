package com.chartboost.helium.helium_common.specification;

public abstract class AbstractSpecification<TypeT> implements Specification<TypeT> {
    abstract public boolean isSatisfiedBy(TypeT thisCandidate);
    public Specification<TypeT> and(Specification<TypeT> rhs) {
        return AndSpecification.from(this, rhs);
    }
    public Specification<TypeT> or(Specification<TypeT> rhs) {
        return OrSpecification.from(this, rhs);
    }
    public Specification<TypeT> not(Specification<TypeT> rhs) {
        return NotSpecification.from(rhs);
    }
}
