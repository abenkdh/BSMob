package com.benkkstudio.bsmob.Interface;

import com.google.android.gms.ads.InterstitialAd;

public interface InterstitialListener {
    void onAdLoaded(InterstitialAd interstitialAd);
    void onAdFailed(InterstitialAd interstitialAd);
    void onAdClosed(InterstitialAd interstitialAd);
}
