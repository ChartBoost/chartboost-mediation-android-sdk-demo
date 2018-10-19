package com.chartboost.helium.helium_domain.ad;

public enum AdState {
    AdState_New,
    AdState_Creating,
    AdState_Ready,
    AdState_RequestToShow,
    AdState_Showing,
    AdState_Closed,
    AdState_Sentinel
}
