package com.dsa.dsadaovang.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.activities.MenuActivity;
import com.dsa.dsadaovang.adapters.AppAdapter;
import com.dsa.dsadaovang.adapters.AppModel;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

@SuppressWarnings("deprecation")
public class OtherAppDialog extends BaseDialog {

    private Button mbtClose;
    private ListView mlvList;
    private TextView mtvTitle;
    private LinearLayout mllTitle;

    public OtherAppDialog(Context context) {
        super(context);
        mtvTitle.setText(mContext.getResources().getString(
                R.string.btn_other_app));
        if (GameUtility.hasConnection()) {
            final String url = "http://gamedsa.com/api/apps_select_app.php";
            final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            final ArrayList<AppModel> appModels = new ArrayList<AppModel>();
            nameValuePairs.add(new BasicNameValuePair("mykey",
                    GameUtility.MY_KEY));
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
                        String[] str = result.split("@");
                        for (int i = 0; i < str.length; i += 3) {
                            AppModel appModel = new AppModel(str[i],
                                    str[i + 1], str[i + 2]);
                            appModels.add(appModel);
                        }
                        mlvList.setAdapter(new AppAdapter(mContext, appModels));
                        mlvList.setOnItemClickListener(new OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0,
                                    View arg1, int pos, long arg3) {
                                AppAdapter adapter = (AppAdapter) mlvList
                                        .getAdapter();
                                MenuActivity.goToMyStore(adapter.getItem(pos)
                                        .getLink());
                            }
                        });
                    }
                    dialog.cancel();
                    show();
                };
            }.execute();
        }
    }

    @Override
    public int getViewId() {
        return R.layout.dialog_list;
    }

    @Override
    public void bindView() {
        mbtClose = (Button) findViewById(R.id.bt_close);
        mbtClose.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GameUtility.mGameSound.play(GameSound.CLICK, 0);
                cancel();
            }
        });
        mtvTitle = (TextView) findViewById(R.id.tv_title);
        mlvList = (ListView) findViewById(R.id.lv_list);
        mllTitle = (LinearLayout) findViewById(R.id.ll_title);
        mllTitle.setVisibility(LinearLayout.GONE);
    }
}
