package com.benkkstudio.bsmob.Interface;

public interface RewardListener {
    void onRewardedVideoAdLoaded();
    void onRewardedVideoAdClosed();
    void onRewardedVideoAdFailedToLoad(int error);
    void onRewardedVideoCompleted();
}
