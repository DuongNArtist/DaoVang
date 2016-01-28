package com.dsa.dsadaovang.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;

public class NextActivity extends Activity {

    private TextView mtvMessage;
    private static NextActivity mInstance;
    public static InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        GameUtility.mGameSound.play(GameSound.START, 0);
        mtvMessage = (TextView) findViewById(R.id.tv_message);
        mtvMessage.setText(getResources().getString(R.string.msg_next_goal,
                GameUtility.mMap.mGameLevel + 1, GameUtility.mMap.mGameGoal));
        mInstance = this;
        if (GameUtility.hasConnection()) {
            loadAd();
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startGame();
                }
            }, 2000);
        }
    }

    public static void loadAd() {
        String key = "ca-app-pub-9404075236503358/9615594020";
        mInterstitialAd = new InterstitialAd(mInstance, key);
        mInterstitialAd.loadAd(new AdRequest());
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onReceiveAd(Ad ad) {
                if (mInterstitialAd.isReady()) {
                    startGame();
                }
            }

            @Override
            public void onPresentScreen(Ad ad) {

            }

            @Override
            public void onLeaveApplication(Ad ad) {

            }

            @Override
            public void onFailedToReceiveAd(Ad ad, ErrorCode errorCode) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        startGame();
                    }
                }, 1000);
            }

            @Override
            public void onDismissScreen(Ad ad) {

            }
        });
    }

    public static void showAd() {
        if (mInterstitialAd != null && mInterstitialAd.isReady()) {
            mInterstitialAd.show();
        }
    }

    private static void startGame() {
        Intent intent = new Intent(mInstance, GameActivity.class);
        mInstance.startActivity(intent);
        mInstance.overridePendingTransition(R.anim.anim_fade_out,
                R.anim.anim_fade_in);
        mInstance.finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
