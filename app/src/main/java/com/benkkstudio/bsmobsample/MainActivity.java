package com.benkkstudio.bsmobsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.benkkstudio.bsmob.BSMob;
import com.benkkstudio.bsmob.Interface.BannerListener;
import com.benkkstudio.bsmob.Interface.InterstitialListener;
import com.benkkstudio.bsmob.Interface.RewardListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    private BSMob bsMob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_click = findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bsMob.showReward();
            }
        });
        bsMob = new BSMob.reward(this)
                .setId("ca-app-pub-3940256099942544/5224354917")
                .setAdRequest(new AdRequest.Builder().build())
                .setListener(new RewardListener() {
                    @Override
                    public void onRewardedVideoAdLoaded() {
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                        Log.d("ABENK : ", "CLOSED");
                        bsMob.loadReward();
                    }

                    @Override
                    public void onRewardedVideoAdFailedToLoad(int error) {
                        Log.d("ABENK : ", "FAILED");
                        bsMob.loadReward();
                    }

                    @Override
                    public void onRewardedVideoCompleted() {
                        Log.d("ABENK : ", "COMPLETE");
                    }
                })
                .show();
//        bsMob = new BSMob.interstitial(this)
//                .setAdRequest(new AdRequest.Builder().build())
//                .setId("ca-app-pub-3940256099942544/1033173712")
//                .setListener(new InterstitialListener() {
//                    @Override
//                    public void onAdLoaded(InterstitialAd interstitialAd) {
//                        Log.d("ABENK :", "LOADED");
//                    }
//
//                    @Override
//                    public void onAdFailed(InterstitialAd interstitialAd) {
//                        Log.d("ABENK :", "FAILED");
//                    }
//
//                    @Override
//                    public void onAdClosed(InterstitialAd interstitialAd) {
//                        bsMob.loadInterstitial();
//                    }
//                })
//                .show();

        final LinearLayout linearLayout = findViewById(R.id.ll_ads);
        new BSMob.banner(this)
                .setAdRequest(new AdRequest.Builder().build())
                .setId("ca-app-pub-3940256099942544/6300978111")
                .setLayout(linearLayout)
                .setSize(AdSize.BANNER)
                .setListener(new BannerListener() {
                    @Override
                    public void onAdFailedToLoad(int error) {
                        linearLayout.setVisibility(View.GONE);
                        Log.d("ABENK : BANNER ", "FAILED");
                    }

                    @Override
                    public void onAdLoaded() {
                        linearLayout.setVisibility(View.VISIBLE);
                        Log.d("ABENK : BANNER ", "LOADED");
                    }
                })
                .show();
    }
}
