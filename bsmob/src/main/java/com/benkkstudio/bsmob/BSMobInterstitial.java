package com.benkkstudio.bsmob;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.benkkstudio.bsmob.Interface.BSMobInterstitialListener;
import com.google.android.gms.ads.AdRequest;

public class BSMobInterstitial extends AbstractInterstitial {

    private BSMobInterstitial(Activity activity, String interstitialId, BSMobInterstitialListener BSMobInterstitialListener, AdRequest adRequest, Boolean repeat) {
        super(activity, interstitialId, BSMobInterstitialListener, adRequest, repeat);
    }


    public static class with {
        private Activity activity;
        private String interstitialId;
        private BSMobInterstitialListener BSMobInterstitialListener;
        private AdRequest adRequest;
        private Boolean repeat;

        public with(@NonNull Activity activity) {
            this.activity = activity;
        }

        @NonNull
        public with setId(@NonNull String interstitialId) {
            this.interstitialId = interstitialId;
            return this;
        }

        @NonNull
        public with setListener(@NonNull BSMobInterstitialListener BSMobInterstitialListener) {
            this.BSMobInterstitialListener = BSMobInterstitialListener;
            return this;
        }

        @NonNull
        public with setAdRequest(@NonNull AdRequest adRequest) {
            this.adRequest = adRequest;
            return this;
        }

        @NonNull
        public with repeatRequest(@NonNull Boolean repeat) {
            this.repeat = repeat;
            return this;
        }
        @NonNull
        public BSMobInterstitial build() {
            return new BSMobInterstitial(activity, interstitialId, BSMobInterstitialListener, adRequest, repeat);
        }
    }

}