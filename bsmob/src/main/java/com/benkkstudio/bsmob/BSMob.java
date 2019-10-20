package com.benkkstudio.bsmob;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.benkkstudio.bsmob.Interface.BannerListener;
import com.benkkstudio.bsmob.Interface.InterstitialListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class BSMob {
    private Activity activity;
    private String interstitialId;
    private InterstitialListener InterstitialListener;
    private AdRequest adRequest;

    private String bannerID;
    private BannerListener bannerListener;
    private AdSize adSize;
    private LinearLayout linearLayout;

    private InterstitialAd interstitialAd;

    private BSMob(Activity activity,
                  String interstitialId,
                  InterstitialListener InterstitialListener,
                  AdRequest adRequest) {
        this.activity = activity;
        this.interstitialId = interstitialId;
        this.InterstitialListener = InterstitialListener;
        this.adRequest = adRequest;
        loadIntersitial();
    }

    private BSMob(Activity activity,
                  AdRequest adRequest,
                  String bannerID,
                  BannerListener bannerListener,
                  AdSize adSize,
                  LinearLayout linearLayout) {
        this.activity = activity;
        this.adRequest = adRequest;
        this.bannerID = bannerID;
        this.bannerListener = bannerListener;
        this.adSize = adSize;
        this.linearLayout = linearLayout;
        loadBanner();
    }

    private void loadBanner(){
        AdView adView = new AdView(activity);
        adView.setAdUnitId(bannerID);
        adView.setAdSize(adSize);
        adView.loadAd(adRequest);
        linearLayout.setVisibility(View.GONE);
        linearLayout.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int error) {
                bannerListener.onAdFailedToLoad(error);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                bannerListener.onAdLoaded();
            }
        });
    }


    private void loadIntersitial(){
        interstitialAd = new InterstitialAd(activity);
        interstitialAd.setAdUnitId(interstitialId);
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                InterstitialListener.onAdClosed(interstitialAd);
            }

            @Override
            public void onAdLoaded() {
                InterstitialListener.onAdLoaded(interstitialAd);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                InterstitialListener.onAdFailed(interstitialAd);
            }
        });
    }

    public void loadInterstitial(){
        interstitialAd.loadAd(adRequest);
    }

    public void showInterstitial(){
        interstitialAd.show();
    }

    public static class interstitial {
        private Activity activity;
        private String interstitialId;
        private InterstitialListener InterstitialListener;
        private AdRequest adRequest;
        private InterstitialAd interstitialAd;

        public interstitial(@NonNull Activity activity) {
            this.activity = activity;
        }

        @NonNull
        public interstitial setId(@NonNull String interstitialId) {
            this.interstitialId = interstitialId;
            return this;
        }

        @NonNull
        public interstitial setListener(@NonNull InterstitialListener InterstitialListener) {
            this.InterstitialListener = InterstitialListener;
            return this;
        }



        @NonNull
        public interstitial setAdRequest(@NonNull AdRequest adRequest) {
            this.adRequest = adRequest;
            return this;
        }

        @NonNull
        public BSMob show() {
            return new BSMob(activity, interstitialId, InterstitialListener, adRequest);
        }
    }

    public static class banner {
        private Activity activity;
        private String bannerID;
        private BannerListener bannerListener;
        private AdSize adSize;
        private LinearLayout linearLayout;
        private AdRequest adRequest;

        public banner(@NonNull Activity activity) {
            this.activity = activity;
        }

        @NonNull
        public banner setId(@NonNull String bannerID) {
            this.bannerID = bannerID;
            return this;
        }

        @NonNull
        public banner setListener(@NonNull BannerListener bannerListener) {
            this.bannerListener = bannerListener;
            return this;
        }

        @NonNull
        public banner setAdRequest(@NonNull AdRequest adRequest) {
            this.adRequest = adRequest;
            return this;
        }

        @NonNull
        public banner setSize(@NonNull AdSize adSize) {
            this.adSize = adSize;
            return this;
        }

        @NonNull
        public banner setLayout(@NonNull LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
            return this;
        }

        @NonNull
        public BSMob show() {
            return new BSMob(activity, adRequest, bannerID, bannerListener, adSize, linearLayout);
        }
    }
}