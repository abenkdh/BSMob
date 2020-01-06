package com.benkkstudio.bsmob;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.benkkstudio.bsmob.Interface.BannerListener;
import com.benkkstudio.bsmob.Interface.InterstitialListener;
import com.benkkstudio.bsmob.Interface.RewardListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class BSMob {
    public static final String DUMMY_BANNER = "ca-app-pub-3940256099942544/6300978111";
    public static final String DUMMY_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712";
    public static final String DUMMY_REWARD = "ca-app-pub-3940256099942544/6300978111";

    private Context context;
    private String interstitialId;
    private InterstitialListener interstitialListener;
    private AdRequest adRequest;

    private String bannerID;
    private BannerListener bannerListener;
    private AdSize adSize;
    private LinearLayout linearLayout;

    private String rewardId;
    private RewardListener rewardListener;

    private static InterstitialAd interstitialAd;
    private static RewardedVideoAd rewardedVideoAd;
    private BSMob(Context context,
                  String interstitialId,
                  InterstitialListener InterstitialListener,
                  AdRequest adRequest) {
        this.context = context;
        this.interstitialId = interstitialId;
        this.interstitialListener = InterstitialListener;
        this.adRequest = adRequest;
        loadInterstitialInline();
    }

    private BSMob(Context context,
                  AdRequest adRequest,
                  String bannerID,
                  BannerListener bannerListener,
                  AdSize adSize,
                  LinearLayout linearLayout) {
        this.context = context;
        this.adRequest = adRequest;
        this.bannerID = bannerID;
        this.bannerListener = bannerListener;
        this.adSize = adSize;
        this.linearLayout = linearLayout;
        loadBannerInline();
    }

    private BSMob(Context context,
                  AdRequest adRequest,
                  String rewardId,
                  RewardListener rewardListener) {
        this.context = context;
        this.adRequest = adRequest;
        this.rewardId = rewardId;
        this.rewardListener = rewardListener;
        loadRewardInline();
    }

    private void loadBannerInline(){
        AdView adView = new AdView(context);
        adView.setAdUnitId(bannerID);
        adView.setAdSize(adSize);
        adView.loadAd(adRequest);
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

    private void loadRewardInline(){
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        rewardedVideoAd.loadAd(rewardId, adRequest);
        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                rewardListener.onRewardedVideoAdLoaded(rewardedVideoAd);
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                rewardListener.onRewardedVideoAdClosed(rewardedVideoAd);
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                rewardListener.onRewardedVideoAdFailedToLoad(i, rewardedVideoAd);
            }

            @Override
            public void onRewardedVideoCompleted() {
                rewardListener.onRewardedVideoCompleted();
            }
        });
    }

    private void loadInterstitialInline(){
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(interstitialId);
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialListener.onAdClosed(interstitialAd);
            }

            @Override
            public void onAdLoaded() {
                interstitialListener.onAdLoaded(interstitialAd);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                interstitialListener.onAdFailed(interstitialAd);
            }
        });
    }

    public static InterstitialAd getInterstitial(){
        return interstitialAd;
    }

    public static RewardedVideoAd getReward(){
        return rewardedVideoAd;
    }

    public static AdSize adaptiveSize(Activity activity, int min) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationBannerAdSizeWithWidth(activity, adWidth - min);
    }

    public static class interstitial {
        private Context context;
        private String interstitialId;
        private InterstitialListener InterstitialListener;
        private AdRequest adRequest;
        private InterstitialAd interstitialAd;
        public interstitial(@NonNull Context context) {
            this.context = context;
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
            return new BSMob(context, interstitialId, InterstitialListener, adRequest);
        }
    }

    public static class banner {
        private Context context;
        private String bannerID;
        private BannerListener bannerListener;
        private AdSize adSize;
        private LinearLayout linearLayout;
        private AdRequest adRequest;
        public banner(@NonNull Context context) {
            this.context = context;
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
            return new BSMob(context, adRequest, bannerID, bannerListener, adSize, linearLayout);
        }
    }

    public static class reward {
        private Context context;
        private String rewardId;
        private RewardListener rewardListener;
        private AdRequest adRequest;

        public reward(@NonNull Context context) {
            this.context = context;
        }

        @NonNull
        public reward setId(@NonNull String rewardId) {
            this.rewardId = rewardId;
            return this;
        }

        @NonNull
        public reward setListener(@NonNull RewardListener rewardListener) {
            this.rewardListener = rewardListener;
            return this;
        }

        @NonNull
        public reward setAdRequest(@NonNull AdRequest adRequest) {
            this.adRequest = adRequest;
            return this;
        }


        @NonNull
        public BSMob show() {
            return new BSMob(context, adRequest, rewardId, rewardListener);
        }
    }
}