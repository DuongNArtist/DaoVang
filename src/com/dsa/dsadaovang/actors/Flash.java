package com.dsa.dsadaovang.actors;

import android.util.Log;

import com.dsa.dsadaovang.engines.GameActor;
import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

public class Flash extends GameActor {

    public static final String TAG = Flash.class.getSimpleName();

    public Flash(float x, float y) {
        super();
        Log.i(TAG, "____________FLASH____________");
        mPositionX = x;
        mPositionY = y;
        GameUtility.mGameSound.play(GameSound.BIG, 0);
        mKey = GameBitmap.FLASH_0;
        init();
        mFramesPerSecond = 20;
        mCurrentFrame = 0;
        mNumberOfFrames = 10;
    }

    @Override
    public void update() {
        if (mAlive) {
            if (System.currentTimeMillis() >= mCurrentTimeMillis + PERIOD
                    / mFramesPerSecond) {
                mCurrentTimeMillis = System.currentTimeMillis();
                mCurrentFrame++;
                if (mCurrentFrame >= mNumberOfFrames) {
                    mCurrentFrame = 0;
                    mAlive = false;
                }
                mBitmap = GameUtility.mGameBitmap.get(mKey + mCurrentFrame);
            }
        }
    }

    @Override
    public int getKeyX() {
        return 0;
    }

}
