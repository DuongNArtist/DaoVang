package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameActor;
import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameUtility;

public class Miner extends GameActor {

    public static final float POSITION_X = 420;
    public static final float POSITION_Y = 70;

    public static final int MAX_EASY = 8;
    public static final int MAX_HARD = 8;
    public static final int MAX_POWER = 4;
    public static final int MAX_SHOOT = 4;
    public static final int MAX_THROW = 4;

    public static final int SHOOT = 0;
    public static final int EASY = 1;
    public static final int HARD = 2;
    public static final int THROW = 3;
    public static final int POWER = 4;

    private boolean mDoing;

    public Miner() {
        super();
        mPositionX = POSITION_X;
        mPositionY = POSITION_Y;
        mFramesPerSecond = 10;
        mCurrentTimeMillis = 0;
        mCurrentFrame = 0;
        mDoing = false;
        mKey = GameBitmap.MINER_SHOOT_0;
        mState = SHOOT;
        init();
    }

    @Override
    public void update() {
        switch (mState) {
        case SHOOT:
            updateMinerShoot();
            break;

        case EASY:
            updateMinerEasy();
            break;

        case HARD:
            updateMinerHard();
            break;

        case THROW:
            updateMinerThrow();
            break;

        case POWER:
            updateMinerPower();
            break;

        default:
            break;
        }
        mBitmap = GameUtility.mGameBitmap.get(mKey + mCurrentFrame);
    }

    @Override
    public int getKeyX() {
        return 0;
    }

    private void updateMinerPower() {
        if (System.currentTimeMillis() >= mCurrentTimeMillis + PERIOD
                / mFramesPerSecond) {
            mCurrentTimeMillis = System.currentTimeMillis();
            mCurrentFrame++;
            if (mCurrentFrame == MAX_POWER) {
                mCurrentFrame = 0;
                mState = SHOOT;
                mKey = GameBitmap.MINER_SHOOT_0;
            }
        }
    }

    private void updateMinerThrow() {
        if (System.currentTimeMillis() >= mCurrentTimeMillis + PERIOD
                / mFramesPerSecond / 2) {
            mCurrentTimeMillis = System.currentTimeMillis();
            mCurrentFrame++;
            if (mCurrentFrame == MAX_THROW) {
                mCurrentFrame = 0;
                mState = SHOOT;
                mKey = GameBitmap.MINER_SHOOT_0;
            }
        }
    }

    private void updateMinerHard() {
        if (mDoing) {
            if (System.currentTimeMillis() >= mCurrentTimeMillis + PERIOD
                    / mFramesPerSecond) {
                mCurrentTimeMillis = System.currentTimeMillis();
                mCurrentFrame = (++mCurrentFrame) % MAX_HARD;
            }
        }
    }

    private void updateMinerEasy() {
        if (mDoing) {
            if (System.currentTimeMillis() >= mCurrentTimeMillis + PERIOD
                    / mFramesPerSecond) {
                mCurrentTimeMillis = System.currentTimeMillis();
                mCurrentFrame = (++mCurrentFrame) % MAX_EASY;
            }
        }
    }

    private void updateMinerShoot() {
        if (mDoing) {
            if (System.currentTimeMillis() >= mCurrentTimeMillis + PERIOD
                    / mFramesPerSecond) {
                mCurrentTimeMillis = System.currentTimeMillis();
                mCurrentFrame++;
                if (mCurrentFrame == MAX_SHOOT) {
                    mCurrentFrame = 0;
                    mState = EASY;
                    mKey = GameBitmap.MINER_EASY_0;
                    mDoing = false;
                }
            }
        }
    }

    public void setDoing(boolean doing) {
        mDoing = doing;
    }

    public boolean isDoing() {
        return mDoing;
    }

}
