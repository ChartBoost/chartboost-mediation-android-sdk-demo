package com.chartboost.helium.helium_interactors.repos;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeliumRepoTest {

    @Test
    public void of() {
        HeliumRepo repo = HeliumRepo.of();
        Assert.assertTrue(repo != null);
    }

    @Test
    public void autoloadAllRepos() {
        HeliumRepo repo = HeliumRepo.of();
        Assert.assertTrue(repo != null);
        repo.autoloadAllRepos();
    }

    @Test
    public void adRepo() {
    }

    @Test
    public void bidRepo() {
    }

    @Test
    public void partnerRepo() {
    }

    @Test
    public void configRepo() {
    }
}