package com.benkkstudio.bsmob;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.benkkstudio.bsmob.Interface.BSMobBannerListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BSMobBanner{
    private Activity activity;
    private String bannerID;
    private BSMobBannerListener bsMobBannerListener;
    private AdRequest adRequest;
    private AdSize adSize;
    private LinearLayout linearLayout;
    private BSMobBanner(Activity activity,
                        String bannerID,
                        BSMobBannerListener bsMobBannerListener,
                        AdRequest adRequest,
                        AdSize adSize,
                        LinearLayout linearLayout) {
    this.activity = activity;
        this.bannerID = bannerID;
        this.bsMobBannerListener = bsMobBannerListener;
        this.adRequest = adRequest;
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
                bsMobBannerListener.onAdFailedToLoad(error);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                bsMobBannerListener.onAdLoaded();
            }
        });
    }

    public static class with {
        private Activity activity;
        private String bannerID;
        private BSMobBannerListener bsMobBannerListener;
        private AdRequest adRequest;
        private AdSize adSize;
        private LinearLayout linearLayout;

        public with(@NonNull Activity activity) {
            this.activity = activity;
        }

        @NonNull
        public with setId(@NonNull String bannerID) {
            this.bannerID = bannerID;
            return this;
        }

        @NonNull
        public with setListener(@NonNull BSMobBannerListener bsMobBannerListener) {
            this.bsMobBannerListener = bsMobBannerListener;
            return this;
        }

        @NonNull
        public with setAdRequest(@NonNull AdRequest adRequest) {
            this.adRequest = adRequest;
            return this;
        }

        @NonNull
        public with setSize(@NonNull AdSize adSize) {
            this.adSize = adSize;
            return this;
        }

        @NonNull
        public with setLayout(@NonNull LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
            return this;
        }

        @NonNull
        public BSMobBanner show() {
            return new BSMobBanner(activity, bannerID, bsMobBannerListener, adRequest, adSize, linearLayout);
        }
    }
}
