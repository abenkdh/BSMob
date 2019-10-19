package com.benkkstudio.bsmob;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.benkkstudio.bsmob.Interface.BSMobOnClosed;
import com.benkkstudio.bsmob.Interface.BSMobOnLoaded;
import com.google.android.gms.ads.AdRequest;

public class BSMobInterstitial extends AbstractInterstitial {

    private BSMobInterstitial(Activity activity, String interstitialId, BSMobOnLoaded BSMobOnLoaded, BSMobOnClosed BSMobOnClosed, AdRequest adRequest, Boolean repeat) {
        super(activity, interstitialId, BSMobOnLoaded, BSMobOnClosed, adRequest, repeat);
    }


    public static class with {
        private Activity activity;
        private String interstitialId;
        private BSMobOnLoaded BSMobOnLoaded;
        private BSMobOnClosed BSMobOnClosed;
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
        public with onLoaded(@NonNull BSMobOnLoaded BSMobOnLoaded) {
            this.BSMobOnLoaded = BSMobOnLoaded;
            return this;
        }

        @NonNull
        public with onClosed(@NonNull BSMobOnClosed BSMobOnClosed) {
            this.BSMobOnClosed = BSMobOnClosed;
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
            return new BSMobInterstitial(activity, interstitialId, BSMobOnLoaded, BSMobOnClosed, adRequest, repeat);
        }
    }

}