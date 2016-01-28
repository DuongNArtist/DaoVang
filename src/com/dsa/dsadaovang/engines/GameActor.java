package com.dsa.dsadaovang.engines;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public abstract class GameActor {

    public static final String TAG = GameActor.class.getSimpleName();

    public static final int PERIOD = 1000;

    protected float mPositionX;
    protected float mPositionY;
    protected float mVelocityX;
    protected float mVelocityY;
    protected int mPaddingX;
    protected int mPaddingY;
    protected int mWidth;
    protected int mHeight;
    protected int mCurrentFrame;
    protected int mNumberOfFrames;
    protected int mFramesPerSecond;
    protected long mCurrentTimeMillis;
    protected boolean mAlive;
    protected boolean mVisible;
    protected int mKey;
    protected int mState;
    protected Random mRandom;
    protected Bitmap mBitmap;
    protected RectF mBounds;
    protected Paint mPaint;

    public GameActor() {
        mRandom = new Random();
        mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.rgb(initColor(), initColor(), initColor()));
        mPaint.setStyle(Paint.Style.STROKE);
        mAlive = true;
        mVisible = true;
    }

    public int initColor() {
        return (int) (255 * mRandom.nextFloat());
    }

    public abstract void update();

    public abstract int getKeyX();

    public void init() {
        mBitmap = GameUtility.mGameBitmap.get(mKey);
        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
        mPaddingX = mWidth / 2;
        mPaddingY = mHeight / 2;
        mBounds = new RectF(mPositionX - mPaddingX * 0.75f, mPositionY
                - mPaddingY * 0.75f, mPositionX + mPaddingX * 0.75f, mPositionY
                + mPaddingY * 0.75f);
        Log.i(TAG, "left = " + mBounds.left);
        Log.i(TAG, "top = " + mBounds.top);
        Log.i(TAG, "right = " + mBounds.right);
        Log.i(TAG, "bottom = " + mBounds.bottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mPositionX - mPaddingX, mPositionY
                - mPaddingY, null);
        // canvas.drawRect(mBounds, mPaint);
    }

    public void setBounds(RectF bounds) {
        mBounds = bounds;
    }

    public RectF getBounds() {
        return mBounds;
    }

    public float getPositionX() {
        return mPositionX;
    }

    public void setPositionX(float positionX) {
        mPositionX = positionX;
    }

    public float getPositionY() {
        return mPositionY;
    }

    public void setPositionY(float positionY) {
        mPositionY = positionY;
    }

    public float getVelocityX() {
        return mVelocityX;
    }

    public void setVelocityX(int velocityX) {
        mVelocityX = velocityX;
    }

    public float getVelocityY() {
        return mVelocityY;
    }

    public void setVelocityY(float velocityY) {
        mVelocityY = velocityY;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getCurrentFrame() {
        return mCurrentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        mCurrentFrame = currentFrame;
    }

    public int getNumberOfFrames() {
        return mNumberOfFrames;
    }

    public void setNumberOfFrames(int numberOfFrames) {
        mNumberOfFrames = numberOfFrames;
    }

    public int getFramesPerSecond() {
        return mFramesPerSecond;
    }

    public void setFramesPerSecond(int framesPerSecond) {
        mFramesPerSecond = framesPerSecond;
    }

    public long getCurrentTimeMillis() {
        return mCurrentTimeMillis;
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
        mCurrentTimeMillis = currentTimeMillis;
    }

    public boolean isAlive() {
        return mAlive;
    }

    public void setAlive(boolean alive) {
        mAlive = alive;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    public int getKey() {
        return mKey;
    }

    public void setKey(int key) {
        mKey = key;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

}
