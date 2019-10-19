package com.benkkstudio.bsmobsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.benkkstudio.bsmob.BSMobBanner;
import com.benkkstudio.bsmob.BSMobInterstitial;
import com.benkkstudio.bsmob.Interface.BSMobBannerListener;
import com.benkkstudio.bsmob.Interface.BSMobOnLoaded;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BSMobInterstitial bsMobInterstitial = new BSMobInterstitial.with(this)
                .setAdRequest(new AdRequest.Builder().build())
                .setId("ca-app-pub-3940256099942544/1033173712")
                .repeatRequest(false)
                .setListener(new BSMobOnLoaded() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        if(interstitialAd.isLoaded()){
                            interstitialAd.show();
                        }
                    }
                })
                .build();
        bsMobInterstitial.load();

        LinearLayout linearLayout = findViewById(R.id.ll_ads);
        new BSMobBanner.with(this)
                .setAdRequest(new AdRequest.Builder().build())
                .setId("ca-app-pub-3940256099942544/1033173711")
                .setLayout(linearLayout)
                .setSize(AdSize.BANNER)
                .setListener(new BSMobBannerListener() {
                    @Override
                    public void onAdFailedToLoad(int error) {

                    }

                    @Override
                    public void onAdLoaded() {

                    }
                })
                .show();
    }
}
