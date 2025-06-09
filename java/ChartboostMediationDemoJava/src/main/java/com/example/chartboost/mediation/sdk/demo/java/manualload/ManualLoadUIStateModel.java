package com.example.chartboost.mediation.sdk.demo.java.manualload;

import java.util.Objects;

public class ManualLoadUIStateModel {

    public final boolean isInterstitialLoadEnabled;
    public final boolean isInterstitialShowEnabled;
    public final boolean isRewardedLoadEnabled;
    public final boolean isRewardedShowEnabled;

    // Constructor to initialize all fields
    public ManualLoadUIStateModel(
            boolean isInterstitialLoadEnabled,
            boolean isInterstitialShowEnabled,
            boolean isRewardedLoadEnabled,
            boolean isRewardedShowEnabled) {
        this.isInterstitialLoadEnabled = isInterstitialLoadEnabled;
        this.isInterstitialShowEnabled = isInterstitialShowEnabled;
        this.isRewardedLoadEnabled = isRewardedLoadEnabled;
        this.isRewardedShowEnabled = isRewardedShowEnabled;
    }

    // "with-..." methods to mimic Kotlin's copy()
    // Each method creates a new instance, changing only the specified field.

    public ManualLoadUIStateModel withInterstitialLoadEnabled(boolean isInterstitialLoadEnabled) {
        return new ManualLoadUIStateModel(
                isInterstitialLoadEnabled, // new value
                this.isInterstitialShowEnabled,
                this.isRewardedLoadEnabled,
                this.isRewardedShowEnabled
        );
    }

    public ManualLoadUIStateModel withInterstitialShowEnabled(boolean isInterstitialShowEnabled) {
        return new ManualLoadUIStateModel(
                this.isInterstitialLoadEnabled,
                isInterstitialShowEnabled, // new value
                this.isRewardedLoadEnabled,
                this.isRewardedShowEnabled
        );
    }

    public ManualLoadUIStateModel withRewardedLoadEnabled(boolean isRewardedLoadEnabled) {
        return new ManualLoadUIStateModel(
                this.isInterstitialLoadEnabled,
                this.isInterstitialShowEnabled,
                isRewardedLoadEnabled, // new value
                this.isRewardedShowEnabled
        );
    }

    public ManualLoadUIStateModel withRewardedShowEnabled(boolean isRewardedShowEnabled) {
        return new ManualLoadUIStateModel(
                this.isInterstitialLoadEnabled,
                this.isInterstitialShowEnabled,
                this.isRewardedLoadEnabled,
                isRewardedShowEnabled // new value
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManualLoadUIStateModel that = (ManualLoadUIStateModel) o;
        return isInterstitialLoadEnabled == that.isInterstitialLoadEnabled &&
                isInterstitialShowEnabled == that.isInterstitialShowEnabled &&
                isRewardedLoadEnabled == that.isRewardedLoadEnabled &&
                isRewardedShowEnabled == that.isRewardedShowEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isInterstitialLoadEnabled, isInterstitialShowEnabled, isRewardedLoadEnabled, isRewardedShowEnabled);
    }

    @Override
    public String toString() {
        return "ManualLoadUIStateModel{" +
                "isInterstitialLoadEnabled=" + isInterstitialLoadEnabled +
                ", isInterstitialShowEnabled=" + isInterstitialShowEnabled +
                ", isRewardedLoadEnabled=" + isRewardedLoadEnabled +
                ", isRewardedShowEnabled=" + isRewardedShowEnabled +
                '}';
    }

    public static ManualLoadUIStateModel initialState() {
        return new ManualLoadUIStateModel(
                true,
                false,
                true,
                false
        );
    }
}
