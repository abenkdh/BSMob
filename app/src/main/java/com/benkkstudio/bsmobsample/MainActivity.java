package com.benkkstudio.bsmobsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.benkkstudio.bsmob.BSMob;
import com.benkkstudio.bsmob.Interface.BSMobBannerListener;
import com.benkkstudio.bsmob.Interface.BSMobInterstitialListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BSMob bsMob = new BSMob.AdmobInterstitial(this)
                .setAdRequest(new AdRequest.Builder().build())
                .setIntersttitialId("ca-app-pub-3940256099942544/1033173712")
                .repeatRequest(false)
                .setListener(new BSMobInterstitialListener() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        if(interstitialAd.isLoaded()){
                            interstitialAd.show();
                        }
                    }
                })
                .build();
        bsMob.load();

    }
}
