package com.softcaze.nicolas.colorchange.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.softcaze.nicolas.colorchange.R;

/**
 * Created by Nicolas on 11/04/2018.
 */

public class LoadingActivity extends Activity {
    protected InterstitialAd interAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        try{
            MobileAds.initialize(this, getResources().getString(R.string.app_ad_it));
        }
        catch (Exception e){
            Log.i("LoadingActivity", "Erreur initalize mobile ad : " + e);
        }


        interAd = new InterstitialAd(LoadingActivity.this);
        interAd.setAdUnitId(getString(R.string.interstitial_unit_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        interAd.loadAd(adRequest);
        interAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (interAd.isLoaded()) {
                    interAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onAdClosed() {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }
}
