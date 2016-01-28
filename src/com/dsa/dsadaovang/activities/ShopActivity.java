package com.dsa.dsadaovang.activities;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

public class ShopActivity extends Activity implements OnClickListener {

    public static final int[] BTN_IDS = { R.id.bt_item_0, R.id.bt_item_1,
            R.id.bt_item_2, R.id.bt_item_3, R.id.bt_item_4, R.id.bt_item_5 };
    public static final int[] TXT_IDS = { R.id.tv_item_0, R.id.tv_item_1,
            R.id.tv_item_2, R.id.tv_item_3, R.id.tv_item_4, R.id.tv_item_5 };

    private int[] mPrices;

    private Button mbtStart;
    private TextView mtvMoney;
    private ImageButton[] mbtItems;
    private TextView[] mtvItems;
    private Animation mAnimation;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        mRandom = new Random();
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        mPrices = new int[BTN_IDS.length];
        mbtItems = new ImageButton[BTN_IDS.length];
        mtvItems = new TextView[TXT_IDS.length];
        mbtStart = (Button) findViewById(R.id.bt_start);
        mbtStart.setOnClickListener(this);
        for (int index = 0; index < mbtItems.length; index++) {
            mbtItems[index] = (ImageButton) findViewById(BTN_IDS[index]);
            mtvItems[index] = (TextView) findViewById(TXT_IDS[index]);
            mbtItems[index].setOnClickListener(this);
        }
        mtvMoney = (TextView) findViewById(R.id.tv_money);
        updateMoney();
        for (int index = 0; index < mPrices.length; index++) {
            mPrices[index] = 200 + mRandom.nextInt(500);
            mtvItems[index].setText(mPrices[index] + "");
            mbtItems[index].setEnabled(true);
            mbtItems[index].setBackgroundResource(R.drawable.and_btn);
        }
        GameSound.getInstance(this).play(GameSound.PASS, 0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_start) {
            onClickStart();
        } else {
            onClickItem((view.getId() - BTN_IDS[0]) / 2);
        }
    }

    private void onClickItem(int index) {
        if (GameUtility.mMap.mGameMoney >= mPrices[index]) {
            GameUtility.mGameSound.play(GameSound.BUY, 0);
            GameUtility.mMap.mGameMoney -= mPrices[index];
            updateMoney();
            mbtItems[index].setEnabled(false);
            mbtItems[index].setBackgroundResource(R.drawable.and_btn_1);
            mbtItems[index].startAnimation(mAnimation);
            switch (index) {
            case 0:
                GameUtility.mMap.mItemDiamond = true;
                break;

            case 1:
                GameUtility.mMap.mItemLucky = true;
                break;

            case 2:
                GameUtility.mMap.mItemMine++;
                break;

            case 3:
                GameUtility.mMap.mItemPower = true;
                break;

            case 4:
                GameUtility.mMap.mItemStone = true;
                break;

            case 5:
                GameUtility.mMap.mItemTime = true;
                GameUtility.mMap.mGameTime += GameUtility.mMap.timeBonus;
                break;

            default:
                break;
            }
        } else {
            GameUtility.mGameSound.play(GameSound.CLICK, 0);
            mtvMoney.startAnimation(mAnimation);
        }
    }

    private void onClickStart() {
        Intent intent = new Intent(this, NextActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_out, R.anim.anim_fade_in);
        finish();
    }

    private void updateMoney() {
        mtvMoney.setText(getResources().getString(R.string.msg_your_score,
                GameUtility.mMap.mGameMoney));
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
