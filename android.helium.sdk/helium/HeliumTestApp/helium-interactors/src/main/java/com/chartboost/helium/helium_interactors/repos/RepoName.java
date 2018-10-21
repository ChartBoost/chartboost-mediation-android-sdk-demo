package com.chartboost.helium.helium_interactors.repos;

public final class RepoName {
    private static String TAG = "RepoName";
    private final String repoName_;
    private RepoName(final String reponame) {
        if (reponame == null) {
            repoName_ = "";
        } else {
            repoName_ = reponame;
        }

    }
    public static RepoName of(final String reponame) {
        return new RepoName(reponame);
    }
    public String name() {
        return repoName_;
    }
}
