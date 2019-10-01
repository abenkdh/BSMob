package com.benkkstudio.bsmob;

import android.app.Activity;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.benkkstudio.bsmob.Interface.BSMobBannerListener;
import com.benkkstudio.bsmob.Interface.BSMobInterstitialListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;

public class AbstractBSMob {
    private Activity activity;
    private String interstitialId;
    private BSMobInterstitialListener BSMobInterstitialListener;
    private AdRequest adRequest;
    private InterstitialAd interstitialAd;
    private Boolean repeat;

    protected AbstractBSMob(Activity activity,
                            String interstitialId,
                            BSMobInterstitialListener BSMobInterstitialListener,
                            AdRequest adRequest,
                            Boolean repeat) {
        this.activity = activity;
        this.interstitialId = interstitialId;
        this.BSMobInterstitialListener = BSMobInterstitialListener;
        this.adRequest = adRequest;
        this.repeat = repeat;

    }

    public void load(){
        interstitialAd = new InterstitialAd(activity);
        interstitialAd.setAdUnitId(interstitialId);
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if(repeat){
                    interstitialAd.loadAd(adRequest);
                }
            }

            @Override
            public void onAdLoaded() {
                BSMobInterstitialListener.onAdLoaded(interstitialAd);
            }
        });
    }

    public void show(){
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }
}
