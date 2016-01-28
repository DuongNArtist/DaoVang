package com.dsa.dsadaovang.engines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.actors.Map;

@SuppressWarnings("deprecation")
public class GameUtility extends Application {

    private static GameUtility mInstance;

    public static Handler mHandler;
    public static Typeface mTypeface;
    public static Resources mResources;
    public static SharedPreferences mPreferences;

    public static Map mMap;
    public static String mName;
    public static int mScore;

    public static final int mGameWidth = 800;
    public static final int mGameHeight = 480;

    public static int mScreenWidth;
    public static int mScreenHeight;

    public static GameBitmap mGameBitmap;
    public static GameSound mGameSound;

    public static final String SP_NAME = "SP_NAME";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_SCORE = "KEY_SCORE";
    public static final String MY_KEY = "SonLam!@#";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static void loadData() {
        mResources = mInstance.getResources();
        mTypeface = Typeface.createFromAsset(mInstance.getAssets(), "font.ttf");
        mPreferences = mInstance.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        initScreenDimensions();
        initGameBitmap();
        initGameSound();
        mMap = new Map();
    }

    private static void initScreenDimensions() {
        DisplayMetrics displayMetrics = mResources.getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    private static void initGameBitmap() {
        mGameBitmap = GameBitmap.getInstance(mInstance);
        for (int id = R.drawable.core_bag_0; id <= R.drawable.core_tnt_1; id++) {
            GameUtility.mGameBitmap.put(id - R.drawable.core_bag_0, id);
        }
    }

    private static void initGameSound() {
        mGameSound = GameSound.getInstance(mInstance);
        for (int id = R.raw.big; id <= R.raw.tnt; id++) {
            GameUtility.mGameSound.put(id - R.raw.big, id);
        }
    }

    public static boolean hasConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }

    public static String getMacAddress() {
        WifiManager wifiManager = (WifiManager) mInstance
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String address = wifiInfo.getMacAddress();
        return address;
    }

    public static String connectUrl(String url,
            List<NameValuePair> nameValuePairs) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        try {
            request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String result = "";
            String line = "";
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return "";
    }

    public static GameUtility getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }

    public static void putBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, true);
    }

    public static void putFloat(String key, float value) {
        mPreferences.edit().putFloat(key, value).commit();
    }

    public static float getFloat(String key) {
        return mPreferences.getFloat(key, 0f);
    }

    public static void putInt(String key, int value) {
        mPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    public static void putLong(String key, long value) {
        mPreferences.edit().putLong(key, value).commit();
    }

    public static long getLong(String key) {
        return mPreferences.getLong(key, 0l);
    }

    public static void putString(String key, String value) {
        mPreferences.edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return mPreferences.getString(key, "");
    }
}
