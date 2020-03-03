package com.benkkstudio.bsmobsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.benkkstudio.bsmob.BSMob;
import com.benkkstudio.bsmob.BSMobConsent;
import com.benkkstudio.bsmob.Interface.BannerListener;
import com.benkkstudio.bsmob.Interface.InterstitialListener;
import com.benkkstudio.bsmob.Interface.InterstitialOnClosed;
import com.benkkstudio.bsmob.Interface.RewardListener;
import com.benkkstudio.bsmob.Interface.RewardOnClosed;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BSMob.interstitial(this)
                .setAdRequest(new AdRequest.Builder().build())
                .setId(BSMob.DUMMY_INTERSTITIAL)
                .load();

        new BSMob.reward(this)
                .setAdRequest(new AdRequest.Builder().build())
                .setId(BSMob.DUMMY_REWARD)
                .load();

        Button btn_click = findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BSMob.showRewardWithListener(new RewardOnClosed() {
                    @Override
                    public void onClosed() {
                        Toast.makeText(MainActivity.this, "onClosed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "onClosed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
