package com.chartboost.helium.helium_interactors;

public class RepoFactory {
    private RepoFactory() {}

    public static RepoFactory of() {
        return new RepoFactory();
    }
}
