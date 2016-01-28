package com.dsa.dsadaovang.actors;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.dsa.dsadaovang.R;
import com.dsa.dsadaovang.engines.GameActor;
import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

public class Rope extends GameActor {

    public static final String TAG = Rope.class.getSimpleName();

    public static final float POSITION_X = 400;
    public static final float POSITION_Y = 80;

    public static final float ROPE_WIDTH = 2.5f;

    public static final float DISTANCE = 50;

    public static final float ANGLE_MIN = 10;
    public static final float ANGLE_SPEED = 1.5f;
    public static final float SPEED = 7.0f;

    public static final int STATE_ROTATE = 0;
    public static final int STATE_SHOOT = 1;
    public static final int STATE_REWIND = 2;

    private int mMoney;
    private float mAngle;
    private float mSpeed;
    private float mDistance;
    private float mHookPositionX;
    private float mHookPositionY;
    private boolean mEmpty;
    private boolean mWay;
    private boolean mBagPower;
    private boolean mBagMine;
    private boolean mBagDiamond;
    private boolean mBagStone;
    private boolean mBagLucky;
    private boolean mBagTime;
    private Miner mMiner;
    private Paint mRopePaint;
    private Matrix mMatrix;
    private Random mRandom;

    public Rope() {
        super();
        mRandom = new Random();
        mMatrix = new Matrix();
        mMiner = new Miner();
        mRopePaint = new Paint();
        mRopePaint.setStyle(Paint.Style.FILL);
        mRopePaint.setStrokeCap(Paint.Cap.ROUND);
        mRopePaint.setStrokeJoin(Paint.Join.ROUND);
        mRopePaint.setStrokeWidth(ROPE_WIDTH);
        mRopePaint.setAntiAlias(true);
        mRopePaint.setFilterBitmap(true);
        mRopePaint.setColor(GameUtility.mResources.getColor(R.color.text));
        mRopePaint.setTypeface(GameUtility.mTypeface);
        mMoney = 0;
        mAngle = 90;
        mEmpty = true;
        mState = STATE_ROTATE;
        mDistance = DISTANCE;
        mKey = GameBitmap.HOOK;
        init();
        updatePosition();
    }

    public void check(ArrayList<Item> items, ArrayList<Boom> booms,
            ArrayList<Flash> flashs) {
        if (mState == STATE_SHOOT) {
            for (Item item : items) {
                RectF rectF = item.getBounds();
                if (rectF.intersect(mBounds)) {
                    Log.i(TAG, "____________Collision____________");
                    item.setAlive(false);
                    mMoney = item.getValue();
                    if (item instanceof BigStone || item instanceof SmallStone
                            || item instanceof BigGold) {
                        rewind(item.getWeight(), item.getKeyX(), Miner.HARD,
                                GameBitmap.MINER_HARD_0);
                    } else {
                        rewind(item.getWeight(), item.getKeyX(), Miner.EASY,
                                GameBitmap.MINER_EASY_0);
                    }
                    if (item instanceof Tnt) {
                        booms.add(new Boom(mPositionX, mPositionY));
                    }
                    if (item instanceof BigGold || item instanceof Diamond) {
                        flashs.add(new Flash(rectF.centerX(), rectF.centerY()));
                    }
                    if (item instanceof PowerBag) {
                        mBagPower = true;
                    }
                    if (item instanceof MineBag) {
                        mBagMine = true;
                    }
                    if (item instanceof StoneBag) {
                        mBagStone = true;
                    }
                    if (item instanceof LuckyBag) {
                        mBagLucky = true;
                    }
                    if (item instanceof TimeBag) {
                        mBagTime = true;
                    }
                    if (item instanceof DiamondBag) {
                        mBagDiamond = true;
                    }
                    break;
                }
            }
        }
    }

    public void action(ArrayList<Boom> booms) {
        if (mState == STATE_ROTATE) {
            GameUtility.mGameSound.play(GameSound.SHOOT, 0);
            mState = STATE_SHOOT;
            mMiner.setKey(GameBitmap.MINER_SHOOT_0);
            mMiner.setState(Miner.SHOOT);
            mMiner.setDoing(true);
        }
        if (mState == STATE_REWIND && GameUtility.mMap.mItemMine > 0 && !mEmpty) {
            GameUtility.mGameSound.play(GameSound.TNT, 0);
            GameUtility.mMap.mItemMine--;
            booms.add(new Boom(mPositionX, mPositionY));
            rotate(Miner.MAX_THROW, GameBitmap.MINER_THROW_0, true);
        }
    }

    @Override
    public void update() {
        mMiner.update();
        switch (mState) {
        case STATE_ROTATE:
            rotating();
            break;

        case STATE_SHOOT:
            shooting();
            break;

        case STATE_REWIND:
            rewinding();
            break;

        default:
            break;
        }
        updatePosition();
    }

    private void updatePosition() {
        mPositionX = POSITION_X + mDistance
                * (float) Math.cos(Math.toRadians(mAngle));
        mPositionY = POSITION_Y + mDistance
                * (float) Math.sin(Math.toRadians(mAngle));
        mHookPositionX = POSITION_X + (mDistance + ROPE_WIDTH)
                * (float) Math.cos(Math.toRadians(mAngle));
        mHookPositionY = POSITION_Y + (mDistance + ROPE_WIDTH)
                * (float) Math.sin(Math.toRadians(mAngle));
        mBounds.set(mHookPositionX - mPaddingX * 0.75f, mHookPositionY
                - mPaddingY * 0.75f, mHookPositionX + mPaddingX * 0.75f,
                mHookPositionY + mPaddingY * 0.75f);
    }

    @Override
    public void draw(Canvas canvas) {
        mMiner.draw(canvas);
        canvas.drawLine(POSITION_X, POSITION_Y, mPositionX, mPositionY,
                mRopePaint);
        mMatrix.postTranslate(-mWidth / 2, -mHeight / 2);
        mMatrix.postRotate(mAngle - 90);
        mMatrix.postTranslate(mHookPositionX, mHookPositionY);
        canvas.drawBitmap(mBitmap, mMatrix, mRopePaint);
        mMatrix.reset();
        // canvas.drawRect(mBounds, mPaint);
    }

    @Override
    public int getKeyX() {
        return 0;
    }

    private void shooting() {
        if (mPositionX <= 0 || mPositionX >= GameUtility.mGameWidth
                || mPositionY >= GameUtility.mGameHeight) {
            rewind(0, 0, Miner.EASY, GameBitmap.MINER_EASY_0);
        } else {
            mDistance += SPEED;
        }
    }

    private void rotating() {
        if (mAngle <= ANGLE_MIN) {
            mWay = false;
        } else if (mAngle >= 180 - ANGLE_MIN) {
            mWay = true;
        }
        if (mWay) {
            mAngle -= ANGLE_SPEED;
        } else {
            mAngle += ANGLE_SPEED;
        }
    }

    public void rotate(int minerState, int minerKey, boolean minerDoing) {
        mState = STATE_ROTATE;
        mDistance = DISTANCE;
        if (!mEmpty) {
            mEmpty = true;
            mKey = GameBitmap.HOOK;
            init();
        }
        mMoney = 0;
        mAngle = ANGLE_MIN + (mRandom.nextFloat()) * (180 - 2 * ANGLE_MIN);
        mMiner.setDoing(minerDoing);
        mMiner.setState(minerState);
        mMiner.setKey(minerKey);
        mMiner.setCurrentFrame(0);
    }

    public void rewind(float weight, int key, int minerState, int minerKey) {
        mState = STATE_REWIND;
        mSpeed = SPEED;
        mSpeed -= weight;
        if (GameUtility.mMap.mItemPower) {
            mSpeed += SPEED;
        }
        if (weight > 0) {
            mEmpty = false;
            mKey = key;
            init();
        } else {
            mEmpty = true;
        }
        mMiner.setState(minerState);
        mMiner.setKey(minerKey);
        mMiner.setDoing(true);
    }

    private void rewinding() {
        if (System.currentTimeMillis() >= mCurrentTimeMillis + PERIOD) {
            mCurrentTimeMillis = System.currentTimeMillis();
            GameUtility.mGameSound.play(GameSound.REWIND, 0);
        }
        mDistance -= mSpeed;
        if (mDistance <= DISTANCE) {
            if (!mEmpty) {
                GameUtility.mGameSound.play(GameSound.EARN, 0);
            }
            if (mBagPower) {
                mBagPower = false;
                GameUtility.mMap.mItemPower = true;
                rotate(Miner.POWER, GameBitmap.MINER_POWER_0, true);
            } else {
                rotate(Miner.SHOOT, GameBitmap.MINER_SHOOT_0, false);
            }
            if (mBagMine) {
                mBagMine = false;
                GameUtility.mMap.mItemMine++;
            }
            if (mBagDiamond) {
                mBagDiamond = false;
                GameUtility.mMap.mItemDiamond = true;
            }
            if (mBagStone) {
                mBagStone = false;
                GameUtility.mMap.mItemStone = true;
            }
            if (mBagLucky) {
                mBagLucky = false;
                GameUtility.mMap.mItemLucky = true;
            }
            if (mBagTime) {
                mBagTime = false;
                GameUtility.mMap.mItemTime = true;
                GameUtility.mMap.mGameTime += GameUtility.mMap.timeBonus;
            }
        }
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        this.mAngle = angle;
    }

    public int getMoney() {
        return mMoney;
    }

    public void setMoney(int money) {
        this.mMoney = money;
    }

    public float getDistance() {
        return mDistance;
    }

    public void setDistance(float distance) {
        this.mDistance = distance;
    }

    public Miner getMiner() {
        return mMiner;
    }

    public void setMiner(Miner miner) {
        this.mMiner = miner;
    }

    public Paint getPaint() {
        return mRopePaint;
    }

    public void setPaint(Paint paint) {
        this.mRopePaint = paint;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public void setEmpty(boolean empty) {
        this.mEmpty = empty;
    }

    public void setWay(boolean way) {
        this.mWay = way;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float speed) {
        this.mSpeed = speed;
    }

    public boolean isEmpty() {
        return mEmpty;
    }

    public boolean isWay() {
        return mWay;
    }

}
