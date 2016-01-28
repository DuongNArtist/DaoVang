package com.dsa.dsadaovang.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.adapters.MoneyAdapter;
import com.dsa.dsadaovang.adapters.MoneyModel;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

@SuppressWarnings("deprecation")
public class RankingDialog extends BaseDialog {

    private Button mbtClose;
    private ListView mlvList;
    private TextView mtvTitle;
    private LinearLayout mllTitle;

    public RankingDialog(Context context) {
        super(context);
        mtvTitle.setText(mContext.getResources()
                .getString(R.string.btn_ranking));
        if (GameUtility.hasConnection()) {
            final String url = "http://gamedsa.com/api/goldminer_select_score.php";
            final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            final ArrayList<MoneyModel> moneyModels = new ArrayList<MoneyModel>();
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
                        int count = 0;
                        String[] str = result.split(" ");
                        for (int i = 0; i < str.length; i += 4) {
                            count++;
                            MoneyModel moneyModel = new MoneyModel(count + "",
                                    str[i], str[i + 1], str[i + 2], str[i + 3]);
                            moneyModels.add(moneyModel);
                        }
                        mlvList.setAdapter(new MoneyAdapter(mContext,
                                moneyModels));
                        mlvList.setOnItemClickListener(null);
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
        mllTitle.setVisibility(LinearLayout.VISIBLE);
    }

}
