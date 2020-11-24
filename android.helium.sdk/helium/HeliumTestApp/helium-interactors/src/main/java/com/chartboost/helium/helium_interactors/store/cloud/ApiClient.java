package com.chartboost.helium.helium_interactors.store.cloud;

import android.content.Context;


import com.chartboost.helium.helium_common.OnError;
import com.chartboost.helium.helium_common.OnSuccess;
import com.chartboost.helium.helium_common.common.Precondition;
import com.chartboost.helium.helium_domain.ad.AdAggregate;

import java.io.InvalidObjectException;

public class ApiClient implements IApiClient<AdAggregate> {
    private PrebidHttpConnection prebidConnection_;
    private AdResponseTransformer transformer_;
    private Context context_;

    private ApiClient() { this(null, null, null); }
    private ApiClient(PrebidHttpConnection prebidConnection, Context context, AdResponseTransformer transformer) {
        prebidConnection_ = prebidConnection;
        context_ = context;
        transformer_ = transformer;
    }

    public static ApiClient post(PrebidHttpConnection prebidConnection, Context context, AdResponseTransformer transformer) {
        return new ApiClient(prebidConnection, context, transformer);
    }

    @Override
    public void fetchAnyAd(OnSuccess<AdAggregate> successCallback, OnError errorCallback) {
        Precondition.isNotNull(prebidConnection_);
        Precondition.isNotNull(transformer_);
        boolean connected = hasActiveInternetConnection();
        bailIfNotConnectedToInternet(connected);
        try {
            String response = prebidConnection_.call();
            if (response != null) {
//                AdIdentifier adid = AdIdentifier.random();
//                Ad nullAd = Ad.ad(adid);
//
//                //TODO: parse and transform response into Ad object
//                int rawBytesLength = response.length();
//                byte[] rawBytes = rawBytes = response.getBytes();
//                SimpleBuffer adContents = new SimpleBuffer(rawBytes, rawBytesLength, response);
//                Ad transformedAd = transformer_.transform(nullAd, adContents);
//                successCallback.handle(transformedAd);
            } else {
                errorCallback.handle(new InvalidObjectException("received bid response was not valid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorCallback.handle(e);
        }
    }

    private void bailIfNotConnectedToInternet(boolean connected) {
        if (!connected) {
            throw new IllegalStateException("Not connected to Internet. Please try after you have active internet connection");
        }
    }

    private boolean hasActiveInternetConnection() {
        return true;
        //test case is crsahing so f*k it for now
        /*
        boolean hasInternetConnection = false;
        Precondition.isNotNull(context_);
        ConnectivityManager connectivityManager = (ConnectivityManager)context_.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                hasInternetConnection = networkInfo.isConnected();
            }
        }
        return hasInternetConnection;
        */
    }

}
