package com.dsa.dsadaovang.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

@SuppressWarnings("deprecation")
public class PauseDialog extends BaseDialog implements
        android.view.View.OnClickListener {

    private Button mbtMenu;
    private Button mbtNext;
    private Button mbtPlay;
    private Button mbtSave;

    private PauseCallBack mPauseCallBack;

    public PauseDialog(Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();
        GameUtility.mMap.mGamePlaying = false;
        if (GameUtility.hasConnection()) {
            loadName();
        } else {
            mbtSave.setVisibility(Button.GONE);
        }
    }

    @Override
    public int getViewId() {
        return R.layout.dialog_pause;
    }

    @Override
    public void bindView() {
        mbtMenu = (Button) findViewById(R.id.bt_menu);
        mbtNext = (Button) findViewById(R.id.bt_next);
        mbtPlay = (Button) findViewById(R.id.bt_play);
        mbtSave = (Button) findViewById(R.id.bt_save);
        mbtMenu.setOnClickListener(this);
        mbtNext.setOnClickListener(this);
        mbtPlay.setOnClickListener(this);
        mbtSave.setOnClickListener(this);
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

    private void onClickSave() {
        if (GameUtility.hasConnection()) {
            String strMac = GameUtility.getMacAddress();
            final String url = "http://gamedsa.com/api/goldminer_update_score.php";
            final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("mykey",
                    GameUtility.MY_KEY));
            nameValuePairs.add(new BasicNameValuePair("mac", strMac));
            nameValuePairs.add(new BasicNameValuePair("level",
                    (GameUtility.mMap.mGameLevel + 1) + ""));
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
                                    mContext.getResources().getString(
                                            R.string.msg_saved),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(
                                    mContext,
                                    mContext.getResources().getString(
                                            R.string.msg_unsaved),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    GameUtility.mMap.mGamePlaying = true;
                    dialog.cancel();
                };
            }.execute();
        }
    }

    public void setCallBack(PauseCallBack callBack) {
        mPauseCallBack = callBack;
    }

    @Override
    public void onClick(View view) {
        GameUtility.mGameSound.play(GameSound.CLICK, 0);
        cancel();
        switch (view.getId()) {
        case R.id.bt_menu:
            mPauseCallBack.menu();
            break;

        case R.id.bt_next:
            mPauseCallBack.next();
            break;

        case R.id.bt_play:
            mPauseCallBack.play();
            break;

        case R.id.bt_save:
            mPauseCallBack.save();
            onClickSave();
            break;

        default:
            break;
        }
    }
}
