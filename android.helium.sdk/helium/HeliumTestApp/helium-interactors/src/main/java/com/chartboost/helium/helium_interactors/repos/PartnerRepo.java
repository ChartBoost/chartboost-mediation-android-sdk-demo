package com.chartboost.helium.helium_interactors.repos;

import com.chartboost.helium.helium_domain.BasicIdentifier;
import com.chartboost.helium.helium_domain.partner.PartnerAggregate;

public class PartnerRepo implements Repo<PartnerAggregate> {
    @Override
    public void get(BasicIdentifier identifier, CompletionCallback<PartnerAggregate> completion) {

    }

    @Override
    public void put(BasicIdentifier identifier, CompletionCallback<PartnerAggregate> completion) {

    }

    @Override
    public String name() {
        return null;
    }
}
