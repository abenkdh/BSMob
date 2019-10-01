package com.benkkstudio.bsmob;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.benkkstudio.bsmob.Interface.BSMobInterstitialListener;
import com.google.android.gms.ads.AdRequest;

public class BSMob extends AbstractBSMob{

    private BSMob(Activity activity, String interstitialId, BSMobInterstitialListener BSMobInterstitialListener, AdRequest adRequest, Boolean repeat) {
        super(activity, interstitialId, BSMobInterstitialListener, adRequest, repeat);
    }


    public static class AdmobInterstitial {
        private Activity activity;
        private String interstitialId;
        private BSMobInterstitialListener BSMobInterstitialListener;
        private AdRequest adRequest;
        private Boolean repeat;

        public AdmobInterstitial(@NonNull Activity activity) {
            this.activity = activity;
        }

        @NonNull
        public AdmobInterstitial setIntersttitialId(@NonNull String interstitialId) {
            this.interstitialId = interstitialId;
            return this;
        }

        @NonNull
        public AdmobInterstitial setListener(@NonNull BSMobInterstitialListener BSMobInterstitialListener) {
            this.BSMobInterstitialListener = BSMobInterstitialListener;
            return this;
        }

        @NonNull
        public AdmobInterstitial setAdRequest(@NonNull AdRequest adRequest) {
            this.adRequest = adRequest;
            return this;
        }

        @NonNull
        public AdmobInterstitial repeatRequest(@NonNull Boolean repeat) {
            this.repeat = repeat;
            return this;
        }
        @NonNull
        public BSMob build() {
            return new BSMob(activity, interstitialId, BSMobInterstitialListener, adRequest, repeat);
        }
    }

}