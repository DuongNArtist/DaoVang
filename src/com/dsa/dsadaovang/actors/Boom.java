package com.dsa.dsadaovang.actors;

import java.util.ArrayList;

import android.graphics.RectF;
import android.util.Log;

import com.dsa.dsadaovang.engines.GameActor;
import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameSound;
import com.dsa.dsadaovang.engines.GameUtility;

public class Boom extends GameActor {

    public static final String TAG = Boom.class.getSimpleName();

    public Boom(float x, float y) {
        super();
        Log.i(TAG, "____________BOOM____________");
        mPositionX = x;
        mPositionY = y;
        GameUtility.mGameSound.play(GameSound.TNT, 0);
        mKey = GameBitmap.BOOM_0;
        init();
        mFramesPerSecond = 20;
        mNumberOfFrames = 10;
        mCurrentFrame = 0;
    }

    public void check(ArrayList<Item> items, ArrayList<Boom> booms) {
        for (Item item : items) {
            RectF rectF = item.getBounds();
            if (rectF.intersect(mBounds)) {
                booms.add(new Boom(item.getPositionX(), item.getPositionY()));
                item.setAlive(false);
            }
        }
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
