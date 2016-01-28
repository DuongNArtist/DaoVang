package com.dsa.dsadaovang.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.dialogs.PauseCallBack;
import com.dsa.dsadaovang.dialogs.PauseDialog;
import com.dsa.dsadaovang.engines.GameUtility;
import com.dsa.dsadaovang.engines.GameView;

@SuppressLint("HandlerLeak")
public class GameActivity extends Activity implements OnClickListener {

    public static final int GAME_OVER = 2358;
    public static final int GAME_WIN = 8532;

    private static Context mContext;
    private FrameLayout mflMain;
    private Button mbtPause;
    private Button mbtPlay;
    private boolean mStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mContext = this;
        mbtPlay = (Button) findViewById(R.id.bt_play);
        mbtPlay.setOnClickListener(this);
        mbtPause = (Button) findViewById(R.id.bt_pause);
        mbtPause.setOnClickListener(this);
        mflMain = (FrameLayout) findViewById(R.id.fl_main);
        GameUtility.mHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what == GAME_WIN) {
                    OverActivity.mLevel++;
                    Intent intent = new Intent(mContext, ShopActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_fade_out,
                            R.anim.anim_fade_in);
                    finish();
                } else if (message.what == GAME_OVER) {
                    Intent intent = new Intent(mContext, OverActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_fade_out,
                            R.anim.anim_fade_in);
                    finish();
                }
            }
        };
        OverActivity.mLevel = GameUtility.mMap.mGameLevel;
        if ((GameUtility.mMap.mGameLevel + 1) % 3 == 0
                && GameUtility.hasConnection()) {
            NextActivity.showAd();
        }
        GameUtility.mMap.mGamePlaying = false;
        mStarted = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mflMain.addView(new GameView(mContext, GameUtility.mMap), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mStarted) {
            mbtPlay.setVisibility(Button.VISIBLE);
            mbtPause.setVisibility(Button.INVISIBLE);
        } else {
            mbtPlay.setVisibility(Button.INVISIBLE);
            mbtPause.setVisibility(Button.VISIBLE);
            GameUtility.mMap.mGamePlaying = true;
        }
        mStarted = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameUtility.mMap.mGamePlaying = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mflMain.removeViewAt(0);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void showPauseDialog() {
        GameUtility.mMap.mGamePlaying = false;
        final PauseDialog quitDialog = new PauseDialog(this);
        quitDialog.setCallBack(new PauseCallBack() {

            @Override
            public void menu() {
                Intent intent = new Intent(mContext, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_fade_out,
                        R.anim.anim_fade_in);
                finish();
                if (GameUtility.hasConnection()) {
                    NextActivity.showAd();
                }
            }

            @Override
            public void next() {
                OverActivity.mLevel++;
                GameUtility.mMap.mGameTime = 0;
                GameUtility.mMap.mGamePlaying = true;
            }

            @Override
            public void play() {
                GameUtility.mMap.mGamePlaying = true;
            }

            @Override
            public void save() {
                GameUtility.mMap.mGamePlaying = false;
            }
        });
        quitDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.bt_pause:
            if (GameUtility.mMap.mGamePlaying) {
                showPauseDialog();
            } else {
                GameUtility.mMap.mGamePlaying = true;
                mbtPause.setText(getResources().getString(R.string.btn_pause));
            }
            break;
        case R.id.bt_play:
            GameUtility.mMap.mGamePlaying = true;
            mbtPause.setVisibility(Button.VISIBLE);
            mbtPlay.setVisibility(Button.INVISIBLE);
            break;

        default:
            break;
        }
    }
}
