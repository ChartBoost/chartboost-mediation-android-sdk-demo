package com.chartboost.helium.heliumtestapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.chartboost.helium.helium_common.bus.HeliumVersion1EventBus;
import com.chartboost.helium.helium_common.event.EventBus;
import com.chartboost.helium.helium_domain.service.SdkRoutingDomainService;
import com.chartboost.helium.helium_infrastructure.Helium;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAd;
import com.chartboost.helium.helium_infrastructure.HeliumInterstitialAdDelegate;
import com.chartboost.helium.helium_infrastructure.HeliumSdk;
import com.chartboost.helium.helium_infrastructure.HeliumSdkInitializeListener;
import com.chartboost.helium.helium_infrastructure.NoAdFoundxception;
import com.chartboost.helium.helium_interactors.RepoFactory;
import com.chartboost.helium.helium_interactors.controllers.AdController;

public class MainActivity extends AppCompatActivity implements HeliumSdkInitializeListener, HeliumInterstitialAdDelegate {

    private Helium helium_;

    private void createHeliumSdk() {
        EventBus eventBus = HeliumVersion1EventBus.of();
        RepoFactory repoFactory = RepoFactory.of();
        SdkRoutingDomainService routingDomainService = SdkRoutingDomainService.of();

        AdController adController = AdController.Builder.of()
            .setEventBus(eventBus)
            .setRepoFactory(repoFactory)
            .setSdkRoutingDomainService(routingDomainService)
            .build();

        helium_ = new HeliumSdk.Builder()
            .setAdController(adController)
            //todo
            .build();

        HeliumSdkInitializeListener initializeListener = this;
        helium_.initialize(initializeListener);

    }


    //HeliumSdkInitializeListener
    @Override
    public void didInitializeHelium(Throwable error) {

        HeliumInterstitialAdDelegate interstitialAdDelegate = this;
        String placementId = "tjPlacementDirectlyUntilPrebidWrapperIsReady";
        HeliumInterstitialAd interstitialAd = helium_.interstitalAd(placementId, interstitialAdDelegate);

        interstitialAd.loadAd();

    }


    //HeliumInterstitialAdDelegate
    @Override
    public void interstitialAdDidLoad(HeliumInterstitialAd heliumInterstitialAd, NoAdFoundxception error) {

    }

    @Override
    public void interstitialAdDidShow(HeliumInterstitialAd heliumInterstitialAd, NoAdFoundxception error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });


        createHeliumSdk();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
