package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameUtility;

public class DiamondPig extends Item {

    public static final float MOVEMENT_SPEED = 1.3f;

    public DiamondPig(float x, float y) {
        super(GameBitmap.PIG_DIAMOND_0, x, y);
        mFramesPerSecond = 10;
        mCurrentTimeMillis = 0;
        mCurrentFrame = 0;
        mVelocityX = -MOVEMENT_SPEED;
    }

    @Override
    public void update() {
        if (mPositionX <= 0) {
            mVelocityX = MOVEMENT_SPEED;
            mKey = GameBitmap.PIG_DIAMOND_5;
        }
        if (mPositionX >= GameUtility.mGameWidth) {
            mVelocityX = -MOVEMENT_SPEED;
            mKey = GameBitmap.PIG_DIAMOND_0;
        }
        if (System.currentTimeMillis() >= mCurrentTimeMillis + PERIOD
                / mFramesPerSecond) {
            mCurrentTimeMillis = System.currentTimeMillis();
            mCurrentFrame = ++mCurrentFrame % 4;
            mBitmap = GameUtility.mGameBitmap.get(mKey + mCurrentFrame);
        }
        mPositionX += mVelocityX;
        mBounds.set(mPositionX - mPaddingX, mPositionY - mPaddingY, mPositionX
                + mPaddingX, mPositionY + mPaddingY);
    }

    @Override
    public int getKeyX() {
        return mKey + 4;
    }

    @Override
    public int getValue() {
        if (GameUtility.mMap.mItemDiamond) {
            return 902;
        } else {
            return 602;
        }
    }

    @Override
    public float getWeight() {
        if (GameUtility.mMap.mItemDiamond) {
            return 2.5f;
        } else {
            return 3.5f;
        }
    }

}
