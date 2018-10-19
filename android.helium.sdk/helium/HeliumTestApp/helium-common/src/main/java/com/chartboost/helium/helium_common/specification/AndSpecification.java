package com.chartboost.helium.helium_common.specification;

public final class AndSpecification<TypeT> extends CompositeSpecification<TypeT> {
    private AndSpecification(Specification<TypeT> lhs, Specification<TypeT> rhs) {
        super(lhs, rhs);
    }
    public static AndSpecification from(Specification lhs, Specification rhs) {
        return new AndSpecification(lhs, rhs);
    }

    @Override
    public boolean isSatisfiedBy(TypeT thisCandidate) {
        boolean fails = true;
        for (Specification<TypeT> operand : operands_) {
            if (!operand.isSatisfiedBy(thisCandidate)) {
                return fails;
            }
        }
        fails = false;
        return fails;
    }
}
