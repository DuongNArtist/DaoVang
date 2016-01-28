package com.dsa.dsadaovang.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.actors.Map;
import com.dsa.dsadaovang.dialogs.LoadDialog;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

@SuppressWarnings("deprecation")
public class OverActivity extends Activity implements OnClickListener {

    private TextView mtvScore;
    private Button mbtMenu;
    private Button mbtSave;
    private Button mbtReplay;
    private Context mContext;
    public static int mLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);
        mContext = this;
        GameUtility.mGameSound.play(GameSound.OVER, 0);
        mbtMenu = (Button) findViewById(R.id.bt_menu);
        mbtMenu.setOnClickListener(this);
        mbtSave = (Button) findViewById(R.id.bt_save);
        mbtSave.setOnClickListener(this);
        mbtReplay = (Button) findViewById(R.id.bt_replay);
        mbtReplay.setOnClickListener(this);
        mtvScore = (TextView) findViewById(R.id.tv_score);
        mtvScore.setText(getResources().getString(R.string.msg_your_score,
                GameUtility.mMap.mGameMoney));
        if (GameUtility.hasConnection()) {
            loadName();
        } else {
            mbtSave.setVisibility(Button.GONE);
        }
    }

    public void loadName() {
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
                        mbtSave.setVisibility(Button.VISIBLE);
                    } else {
                        mbtSave.setVisibility(Button.GONE);
                    }
                    dialog.cancel();
                };
            }.execute();
        }
    }

    @Override
    public void onClick(View view) {
        GameUtility.mGameSound.play(GameSound.CLICK, 0);
        switch (view.getId()) {
        case R.id.bt_menu:
            onClickMenu();
            break;

        case R.id.bt_save:
            onClickSave();
            break;

        case R.id.bt_replay:
            onClickReplay();
            break;

        default:
            break;
        }

    }

    private void onClickReplay() {
        GameUtility.mMap = new Map();
        Intent intent = new Intent(this, NextActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_out, R.anim.anim_fade_in);
        finish();
    }

    private void onClickSave() {
        if (GameUtility.hasConnection()) {
            String strMac = GameUtility.getMacAddress();
            final String url = "http://gamedsa.com/api/goldminer_update_score.php";
            final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("mykey",
                    GameUtility.MY_KEY));
            nameValuePairs.add(new BasicNameValuePair("mac", strMac));
            nameValuePairs.add(new BasicNameValuePair("level", mLevel + ""));
            nameValuePairs.add(new BasicNameValuePair("score",
                    GameUtility.mMap.mGameMoney + ""));
            final LoadDialog dialog = new LoadDialog(mContext);
            new AsyncTask<Void, Void, String>() {

                @Override
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
                    if (!result.equals("null")) {
                        int res = 0;
                        if (!result.trim().equals("")) {
                            res = Integer.parseInt(result.trim());
                        }
                        if (res != 0) {
                            mbtSave.setVisibility(Button.GONE);
                            Toast.makeText(
                                    mContext,
                                    getResources()
                                            .getString(R.string.msg_saved),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(
                                    mContext,
                                    getResources().getString(
                                            R.string.msg_unsaved),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    dialog.cancel();
                };
            }.execute();
        }
    }

    private void onClickMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_out, R.anim.anim_fade_in);
        finish();
        if (GameUtility.hasConnection()) {
            NextActivity.showAd();
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
