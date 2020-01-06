package com.benkkstudio.bsmobsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.benkkstudio.bsmob.BSMob;
import com.benkkstudio.bsmob.BSMobConsent;
import com.benkkstudio.bsmob.Interface.BannerListener;
import com.benkkstudio.bsmob.Interface.InterstitialListener;
import com.benkkstudio.bsmob.Interface.RewardListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    private BSMob bsMob;
    private BSMobConsent bsMobConsent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_click = findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BSMob.getInterstitial().show();
            }
        });

        bsMobConsent = new BSMobConsent.Builder(this)
                .addTestDeviceId("984B8E996B246B35F1D0726C95287E5A")
                .addCustomLogTag("ABENK")
                .addPrivacyPolicy("http://benkkstudio.xyz")
                .addPublisherId("pub-3940256099942544")
                .build();

        bsMobConsent.checkConsent(new BSMobConsent.ConsentCallback() {
            @Override
            public void onResult(boolean isRequestLocationInEeaOrUnknown) {
                Log.wtf("ABENK : ", String.valueOf(isRequestLocationInEeaOrUnknown));
            }
        });


        bsMob = new BSMob.interstitial(this)
                .setAdRequest(new AdRequest.Builder().build())
                .setId("ca-app-pub-3940256099942544/1033173712")
                .setListener(new InterstitialListener() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        Log.d("ABENK :", "LOADED");
                    }

                    @Override
                    public void onAdFailed(InterstitialAd interstitialAd) {
                        Log.d("ABENK :", "FAILED");
                    }

                    @Override
                    public void onAdClosed(InterstitialAd interstitialAd) {
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                })
                .show();

        final LinearLayout linearLayout = findViewById(R.id.ll_ads);
        new BSMob.banner(this)
                .setAdRequest(bsMobConsent.getAdRequest())
                .setId("ca-app-pub-3940256099942544/6300978111")
                .setLayout(linearLayout)
                .setSize(BSMob.adaptiveSize(this, 0))
                .setListener(new BannerListener() {
                    @Override
                    public void onAdFailedToLoad(int error) {
                        linearLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdLoaded() {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                })
                .show();
    }
}
