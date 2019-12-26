package com.benkkstudio.bsmob.Interface;

import com.google.android.gms.ads.reward.RewardedVideoAd;

public interface RewardListener {
    void onRewardedVideoAdLoaded(RewardedVideoAd rewardedVideoAd);
    void onRewardedVideoAdClosed(RewardedVideoAd rewardedVideoAd);
    void onRewardedVideoAdFailedToLoad(int error, RewardedVideoAd rewardedVideoAd);
}
