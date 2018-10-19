package com.chartboost.helium.helium_common.specification;

public final class NotSpecification<TypeT> implements Specification<TypeT> {
    private final Specification<TypeT> check_;
    private NotSpecification(Specification<TypeT> target) {
        check_ = target;
    }
    public static NotSpecification from(Specification target) {
        return new NotSpecification(target);
    }
    @Override
    public boolean isSatisfiedBy(TypeT thisCandidate) {
        return !(check_.isSatisfiedBy(thisCandidate));
    }
}
