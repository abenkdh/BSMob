package com.benkkstudio.bsmob;

import android.app.Activity;

import com.benkkstudio.bsmob.Interface.BSMobOnClosed;
import com.benkkstudio.bsmob.Interface.BSMobOnLoaded;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AbstractInterstitial {
    private Activity activity;
    private String interstitialId;
    private BSMobOnLoaded BSMobOnLoaded;
    private BSMobOnClosed BSMobOnClosed;
    private AdRequest adRequest;
    private InterstitialAd interstitialAd;
    private Boolean repeat;

    protected AbstractInterstitial(Activity activity,
                                   String interstitialId,
                                   BSMobOnLoaded BSMobOnLoaded,
                                   BSMobOnClosed BSMobOnClosed,
                                   AdRequest adRequest,
                                   Boolean repeat) {
        this.activity = activity;
        this.interstitialId = interstitialId;
        this.BSMobOnLoaded = BSMobOnLoaded;
        this.BSMobOnClosed = BSMobOnClosed;
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
                if(BSMobOnClosed != null){
                    BSMobOnClosed.onAdClosed(interstitialAd);
                } else {
                    if(repeat){
                        interstitialAd.loadAd(adRequest);
                    }
                }
            }

            @Override
            public void onAdLoaded() {
                BSMobOnLoaded.onAdLoaded(interstitialAd);
            }
        });
    }

    public boolean isLoaded(){
        return interstitialAd.isLoaded();
    }
    
    public void show(){
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }
}
