package com.chartboost.helium.helium_interactors.repos;

import com.chartboost.helium.helium_interactors.store.factory.HeliumStore;

import java.util.concurrent.ConcurrentHashMap;

public final class HeliumRepo {
    private ConcurrentHashMap<RepoName, Object> loadedRepos_;
    private HeliumStore heliumStore_;
    private HeliumRepo() {
        loadedRepos_ = new ConcurrentHashMap<>();
    }
    public static HeliumRepo of() {
        return new HeliumRepo();
    }

    public void autoloadAllRepos() {

    }
    public AdRepo adRepo() {
        return new AdRepo();
    }
    public BidRepo bidRepo() {
        return null;
    }
    public PartnerRepo partnerRepo() {
        return null;
    }
    public ConfigRepo configRepo() {
        return null;
    }
}
