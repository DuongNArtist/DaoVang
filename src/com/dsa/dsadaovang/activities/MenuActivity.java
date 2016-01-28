package com.dsa.dsadaovang.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.actors.Map;
import com.dsa.dsadaovang.dialogs.InstructionsDialog;
import com.dsa.dsadaovang.dialogs.LoadDialog;
import com.dsa.dsadaovang.dialogs.OtherAppDialog;
import com.dsa.dsadaovang.dialogs.RankingDialog;
import com.dsa.dsadaovang.dialogs.RegisterDialog;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

@SuppressWarnings("deprecation")
public class MenuActivity extends Activity implements OnClickListener {

    private Button mbtOtherApps;
    private Button mbtRateMe;
    private Button mbtRanking;
    private Button mbtInstruction;
    private Button mbtSound;
    private Button mbtQuit;
    private Button mbtStart;
    private static TextView mtvName;
    private static TextView mtvScore;
    private LinearLayout mllAccount;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mContext = this;
        mllAccount = (LinearLayout) findViewById(R.id.ll_account);
        mtvName = (TextView) findViewById(R.id.tv_name);
        mtvScore = (TextView) findViewById(R.id.tv_score);
        mbtOtherApps = (Button) findViewById(R.id.bt_other_apps);
        mbtRateMe = (Button) findViewById(R.id.bt_rate_me);
        mbtRanking = (Button) findViewById(R.id.bt_ranking);
        mbtInstruction = (Button) findViewById(R.id.bt_instruction);
        mbtSound = (Button) findViewById(R.id.bt_sound);
        mbtQuit = (Button) findViewById(R.id.bt_quit);
        mbtStart = (Button) findViewById(R.id.bt_start);
        mbtOtherApps.setOnClickListener(this);
        mbtRateMe.setOnClickListener(this);
        mbtRanking.setOnClickListener(this);
        mbtInstruction.setOnClickListener(this);
        mbtSound.setOnClickListener(this);
        mbtQuit.setOnClickListener(this);
        mbtStart.setOnClickListener(this);
        if (GameUtility.mGameSound.isSound()) {
            mbtSound.setText(getResources().getString(R.string.stt_on));
        } else {
            GameUtility.mGameSound.onSound();
            mbtSound.setText(getResources().getString(R.string.stt_off));
        }
        if (GameUtility.hasConnection()) {
            mbtOtherApps.setVisibility(Button.VISIBLE);
            mbtRanking.setVisibility(Button.VISIBLE);
            mbtRateMe.setVisibility(Button.VISIBLE);
            mllAccount.setVisibility(LinearLayout.VISIBLE);
        } else {
            mbtOtherApps.setVisibility(Button.GONE);
            mbtRanking.setVisibility(Button.GONE);
            mbtRateMe.setVisibility(Button.GONE);
            mllAccount.setVisibility(LinearLayout.GONE);
        }
        loadName();
    }

    @Override
    public void onClick(View view) {
        GameUtility.mGameSound.play(GameSound.CLICK, 0);
        switch (view.getId()) {
        case R.id.bt_other_apps:
            onClickOtherApps();
            break;

        case R.id.bt_rate_me:
            onClickRateMe();
            break;

        case R.id.bt_ranking:
            onClickRanking();
            break;

        case R.id.bt_instruction:
            onClickInstruction();
            break;

        case R.id.bt_sound:
            onClickSound();
            break;

        case R.id.bt_quit:
            onClickQuit();
            break;

        case R.id.bt_start:
            onClickStart();
            break;

        default:
            break;
        }
    }

    private void onClickStart() {
        GameUtility.mMap = new Map();
        Intent intent = new Intent(this, NextActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_out, R.anim.anim_fade_in);
        finish();
    }

    private void onClickQuit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.gc();
        System.exit(0);
    }

    private void onClickSound() {
        if (GameUtility.mGameSound.isSound()) {
            GameUtility.mGameSound.offSound();
            mbtSound.setText(getResources().getString(R.string.stt_off));
        } else {
            GameUtility.mGameSound.onSound();
            mbtSound.setText(getResources().getString(R.string.stt_on));
        }
    }

    private void onClickInstruction() {
        new InstructionsDialog(mContext);
    }

    private void onClickRanking() {
        new RankingDialog(mContext);
    }

    private void onClickRateMe() {
        goToMyStore("market://details?id="
                + getApplicationContext().getPackageName());
    }

    private void onClickOtherApps() {
        new OtherAppDialog(mContext);
    }

    public static void goToMyStore(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(path));
        mContext.startActivity(intent);
    }

    public static void loadName() {
        if (GameUtility.hasConnection()) {
            final String strMac = GameUtility.getMacAddress();
            final String url = "http://gamedsa.com/api/goldminer_select_name.php";
            final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("mykey",
                    GameUtility.MY_KEY));
            nameValuePairs.add(new BasicNameValuePair("mac", strMac));
            final LoadDialog dialog = new LoadDialog(mContext);
            new AsyncTask<Void, Void, String>() {
                protected void onPreExecute() {
                    dialog.show();
                };

                @Override
                protected String doInBackground(Void... params) {
                    String result = GameUtility.connectUrl(url, nameValuePairs);
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    if (result.length() > 0 && !result.equals("null")) {
                        Log.i("RESULT", "'" + result + "'");
                        String[] strings = result.split(" ");
                        mtvName.setText(strings[0]);
                        mtvScore.setText(strings[1]);
                    } else {
                        RegisterDialog dialog = RegisterDialog
                                .getInstance(mContext);
                        dialog.show();
                    }
                    dialog.cancel();
                };
            }.execute();
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
