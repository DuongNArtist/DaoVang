package com.dsa.dsadaovang.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.engines.GameUtility;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MainActivity extends Activity implements Runnable {

    public static DisplayImageOptions mDisplayImageOptions;
    public static ImageLoaderConfiguration mImageLoaderConfiguration;
    public static ImageLoader mImageLoader;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        new Thread(this).start();
    }

    @Override
    public void run() {
        GameUtility.loadData();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
                mContext).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(mDisplayImageOptions).build();
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
        }
        mImageLoader.init(mImageLoaderConfiguration);
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_out, R.anim.anim_fade_in);
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
