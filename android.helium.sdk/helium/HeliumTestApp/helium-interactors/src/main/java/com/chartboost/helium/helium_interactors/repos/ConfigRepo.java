package com.chartboost.helium.helium_interactors.repos;

import com.chartboost.helium.helium_domain.BasicIdentifier;
import com.chartboost.helium.helium_domain.config.Config;

public class ConfigRepo implements Repo<Config>  {
    @Override
    public void get(BasicIdentifier identifier, CompletionCallback<Config> completion) {

    }

    @Override
    public void put(BasicIdentifier identifier, CompletionCallback<Config> completion) {

    }

    @Override
    public String name() {
        return null;
    }
}
