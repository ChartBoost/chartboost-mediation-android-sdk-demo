package com.chartboost.helium.helium_domain.ad;

import com.chartboost.helium.helium_domain.BasicAggregate;
import com.chartboost.helium.helium_domain.BasicIdentifier;
import com.chartboost.helium.helium_domain.PlacementId;

public class AdAggregate extends BasicAggregate {
    private BasicIdentifier adId_;
    private BasicIdentifier bidId_;
    private BasicIdentifier partnerId_;
    private PlacementId placementId_;

    private TimeUnit expireTime_;
    private AdExpiryTimer adExpiryTimer_;
    private AdState state_;

    public AdAggregate() {
        state_ = AdState.AdState_New;
        startAdExpiryTimer();
    }


    @Override
    public BasicIdentifier identifier() {
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

    public void setIdentifier(BasicIdentifier identifier) {
        adId_ = identifier;
        state_ = AdState.AdState_Creating;
    }

    public void setPlacementId(PlacementId placementId) {
        placementId_ = placementId;
    }
}
