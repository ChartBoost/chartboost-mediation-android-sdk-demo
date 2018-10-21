package com.chartboost.helium.helium_interactors.repos;

import java.util.concurrent.ConcurrentHashMap;

public class HeliumRepo {
    private ConcurrentHashMap<RepoName, Object> loadedRepos_;

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
