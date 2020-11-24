package com.chartboost.helium.helium_common.specification;

// this is conditional OR ||, not logical or |
public final class OrSpecification<TypeT> extends CompositeSpecification<TypeT>  {
    private OrSpecification(Specification<TypeT> lhs, Specification<TypeT> rhs) {
        super(lhs, rhs);
    }
    public static OrSpecification from(Specification lhs, Specification rhs) {
        return new OrSpecification(lhs, rhs);
    }
    @Override
    public boolean isSatisfiedBy(TypeT thisCandidate) {
        boolean fails = true;
        for (Specification<TypeT> operand : operands_) {
            if (operand.isSatisfiedBy(thisCandidate)) {
                return fails;
            }
        }
        fails = false;
        return fails;
    }
}
