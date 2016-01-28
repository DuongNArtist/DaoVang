package com.dsa.dsadaovang.actors;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.activities.GameActivity;
import com.dsa.dsadaovang.engines.GameActor;
import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameUtility;

public class Map extends GameActor {

    public static final String TAG = Map.class.getSimpleName();

    public static final int[] GOALS = { 650, 500, 860, 1085, 1355, 1700, 1820,
            2265, 2535, 3005 };

    public final int timeBonus = 15;
    public final int timeInit = 60;
    public final int textSize = 20;

    public int mGameMoney;
    public int mGameLevel;
    public int mGameGoal;
    public int mGameTime;
    public int mGameBonus;
    public boolean mGamePlaying;

    public int mItemMine;
    public boolean mItemDiamond;
    public boolean mItemLucky;
    public boolean mItemPower;
    public boolean mItemStone;
    public boolean mItemTime;

    private int mBackground;
    private Rope mRope;
    private ArrayList<Boom> mBooms;
    private ArrayList<Flash> mFlashs;
    private ArrayList<Item> mItems;

    private String mStrMoney;
    private String mStrLevel;
    private String mStrGoal;
    private Paint mTextPaint;
    private Paint mTimePaint;

    public Map() {
        super();
        mGamePlaying = false;
        mStrMoney = GameUtility.mResources.getString(R.string.str_money);
        mStrLevel = GameUtility.mResources.getString(R.string.str_level);
        mStrGoal = GameUtility.mResources.getString(R.string.str_goal);
        mWidth = GameUtility.mGameWidth;
        mHeight = GameUtility.mGameHeight;
        mGamePlaying = false;

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(GameUtility.mResources.getColor(R.color.text));
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTypeface(GameUtility.mTypeface);

        mTimePaint = new Paint();
        mTimePaint.setAntiAlias(true);
        mTimePaint.setColor(GameUtility.mResources.getColor(R.color.text));
        mTimePaint.setTextSize(2 * textSize);
        mTimePaint.setTypeface(GameUtility.mTypeface);
        onCreateMap();
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        mRope.action(mBooms);
    }

    public void onCreateMap() {
        mRope = new Rope();
        mBooms = new ArrayList<Boom>();
        mFlashs = new ArrayList<Flash>();
        mGameGoal = 0;
        mGameBonus = 0;
        for (int i = 0; i <= mGameLevel; i++) {
            mGameGoal += GOALS[i % 10];
        }
        mGameTime = timeInit;
        mBackground = GameBitmap.BKG_0 + mGameLevel % 7;
        mItems = Item.generate(mGameLevel);
    }

    private void updateBoom() {
        for (int index = 0; index < mBooms.size(); index++) {
            Boom boom = mBooms.get(index);
            boom.update();
            if (!boom.isAlive()) {
                boom.check(mItems, mBooms);
                mBooms.remove(index);
            }
        }
    }

    private void updateFlash() {
        for (int index = 0; index < mFlashs.size(); index++) {
            Flash flash = mFlashs.get(index);
            flash.update();
            if (!flash.isAlive()) {
                mFlashs.remove(index);
            }
        }
    }

    private void updateRope() {
        mRope.update();
        mRope.check(mItems, mBooms, mFlashs);
        for (int index = 0; index < mItems.size(); index++) {
            Item item = mItems.get(index);
            item.update();
            if (!item.isAlive()) {
                mItems.remove(index);
                mGameBonus = mRope.getMoney();
            }
        }
        if (mRope.getState() == Rope.STATE_ROTATE) {
            mGameMoney += mGameBonus;
            mGameBonus = 0;
        }
    }

    @Override
    public void update() {
        if (mGamePlaying) {
            if (System.currentTimeMillis() > mCurrentTimeMillis + 1000) {
                mCurrentTimeMillis = System.currentTimeMillis();
                mGameTime--;
            }
            if (mGameTime > 0) {
                updateRope();
                updateBoom();
                updateFlash();
            } else {
                Message message = new Message();
                if (mGameMoney >= mGameGoal) {
                    mGameLevel++;
                    message.what = GameActivity.GAME_WIN;
                } else {
                    mGameLevel = 0;
                    message.what = GameActivity.GAME_OVER;
                }
                resetItem();
                onCreateMap();
                mGamePlaying = false;
                GameUtility.mHandler.sendMessage(message);
            }
        }
    }

    public void resetItem() {
        if (mGameLevel == 0) {
            mItemMine = 0;
        }
        mItemDiamond = false;
        mItemLucky = false;
        mItemStone = false;
        mItemPower = false;
        mItemTime = false;
    }

    public Rope getRope() {
        return mRope;
    }

    public void setRope(Rope rope) {
        this.mRope = rope;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public ArrayList<Boom> getBooms() {
        return mBooms;
    }

    public void setBooms(ArrayList<Boom> booms) {
        this.mBooms = booms;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(GameUtility.mGameBitmap.get(mBackground), 0, 0, null);
        if (mGamePlaying) {
            drawText(canvas);
            drawItem(canvas);
        }
        drawShop(canvas);
        mRope.draw(canvas);
        drawBoom(canvas);
        drawFlash(canvas);
    }

    private void drawShop(Canvas canvas) {
        if (mItemDiamond) {
            canvas.drawBitmap(
                    GameUtility.mGameBitmap.get(GameBitmap.ITEM_DIAMOND), 280,
                    80, null);
        }
        if (mItemStone) {
            canvas.drawBitmap(
                    GameUtility.mGameBitmap.get(GameBitmap.ITEM_STONE), 320,
                    75, null);
        }
        if (mItemPower) {
            canvas.drawBitmap(
                    GameUtility.mGameBitmap.get(GameBitmap.ITEM_POWER), 480,
                    70, null);
        }
        if (mItemTime) {
            canvas.drawBitmap(
                    GameUtility.mGameBitmap.get(GameBitmap.ITEM_TIME), 530, 75,
                    null);
        }
        if (mItemLucky) {
            canvas.drawBitmap(
                    GameUtility.mGameBitmap.get(GameBitmap.ITEM_LUCKY), 370, 0,
                    null);
        }
        if (mItemMine > 0) {
            canvas.drawText("x" + mItemMine, 470, 25, mTextPaint);
            canvas.drawBitmap(
                    GameUtility.mGameBitmap.get(GameBitmap.ITEM_MINE), 450, 0,
                    null);
        }
    }

    private void drawFlash(Canvas canvas) {
        for (Flash flash : mFlashs) {
            flash.draw(canvas);
        }
    }

    private void drawBoom(Canvas canvas) {
        for (Boom boom : mBooms) {
            boom.draw(canvas);
        }
    }

    private void drawItem(Canvas canvas) {
        for (Item item : mItems) {
            item.draw(canvas);
        }
    }

    private void drawText(Canvas canvas) {
        if (mGameBonus > 0) {
            canvas.drawText(mGameBonus + "", 400, 20, mTextPaint);
        }
        canvas.drawText(mStrLevel + (mGameLevel + 1), 90, 35, mTextPaint);
        canvas.drawText(mStrGoal + mGameGoal, 90, 60, mTextPaint);
        canvas.drawText(mStrMoney + mGameMoney, 90, 85, mTextPaint);
        canvas.drawText(mGameTime + "", 665, 80, mTimePaint);
    }

    @Override
    public int getKeyX() {
        return 0;
    }

}
