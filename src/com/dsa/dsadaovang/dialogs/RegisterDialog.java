package com.dsa.dsadaovang.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.activities.MenuActivity;
import com.dsa.dsadaovang.engines.GameUtility;

@SuppressWarnings("deprecation")
public class RegisterDialog extends BaseDialog implements
        android.view.View.OnClickListener {

    private static RegisterDialog mInstance;

    private Button mbtRegister;
    private EditText medName;

    private RegisterDialog(Context context) {
        super(context);
    }

    public static RegisterDialog getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RegisterDialog(context);
        }
        return mInstance;
    }

    @Override
    public int getViewId() {
        return R.layout.dialog_register;
    }

    @Override
    public void bindView() {
        mbtRegister = (Button) findViewById(R.id.bt_register);
        mbtRegister.setOnClickListener(this);
        medName = (EditText) findViewById(R.id.ed_name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.bt_register:
            onClickRegister();
            break;

        default:
            break;
        }
    }

    private void onClickRegister() {
        if (GameUtility.hasConnection()) {
            String strName = medName.getText().toString().trim();
            if (strName.length() <= 15 && strName.length() >= 3
                    && !strName.contains(" ")) {
                String strMac = GameUtility.getMacAddress();
                final String url = "http://gamedsa.com/api/goldminer_insert_name.php";
                final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mykey",
                        GameUtility.MY_KEY));
                nameValuePairs.add(new BasicNameValuePair("mac", strMac));
                nameValuePairs.add(new BasicNameValuePair("name", strName));
                final LoadDialog dialog = new LoadDialog(mContext);
                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected void onPreExecute() {
                        dialog.show();
                    }

                    @Override
                    protected String doInBackground(Void... params) {
                        String result = GameUtility.connectUrl(url,
                                nameValuePairs);
                        return result;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        Log.i("Register", result);
                        if (result.length() > 0 && !result.trim().equals("")) {
                            int res = 0;
                            res = Integer.parseInt(result.trim());
                            if (res != 0) {
                                Toast.makeText(
                                        mContext,
                                        mContext.getResources().getString(
                                                R.string.msg_registed),
                                        Toast.LENGTH_SHORT).show();
                                MenuActivity.loadName();
                                mInstance.cancel();
                            } else {
                                Toast.makeText(
                                        mContext,
                                        mContext.getResources().getString(
                                                R.string.msg_unregisted),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(
                                    mContext,
                                    mContext.getResources().getString(
                                            R.string.msg_unregisted),
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    };
                }.execute();
            } else {
                Toast.makeText(
                        mContext,
                        mContext.getResources().getString(R.string.msg_invalid),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
