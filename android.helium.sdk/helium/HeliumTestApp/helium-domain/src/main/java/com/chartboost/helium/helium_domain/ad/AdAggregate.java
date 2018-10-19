package com.chartboost.helium.helium_domain.ad;

import com.chartboost.helium.helium_domain.BasicAggregate;
import com.chartboost.helium.helium_domain.Idenitifer;
import com.chartboost.helium.helium_domain.PlacementId;

public class AdAggregate extends BasicAggregate {
    private Idenitifer adId_;
    private Idenitifer bidId_;
    private Idenitifer partnerId_;
    private PlacementId placementId_;

    private TimeUnit expireTime_;
    private AdExpiryTimer adExpiryTimer_;
    private AdState state_;


    @Override
    public Idenitifer identifier() {
        return adId_;
    }

    public void startAdExpiryTimer() {
        if (adExpiryTimer_ != null) {
            adExpiryTimer_.invalidate();
            adExpiryTimer_ = null;
        }
        adExpiryTimer_ = AdExpiryTimer.adExpiryTimer(expireTime_);
        adExpiryTimer_.start();
    }

    public void setIdentifier(Idenitifer identifier) {
        adId_ = identifier;
    }

    public void setPlacementId(PlacementId placementId) {
        placementId_ = placementId;
    }
}
