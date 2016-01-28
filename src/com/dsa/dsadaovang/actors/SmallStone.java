package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameUtility;

public class SmallStone extends Item {

    public SmallStone(float x, float y) {
        super(GameBitmap.STONE_SMALL_0, x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public int getKeyX() {
        return mKey + 1;
    }

    @Override
    public int getValue() {
        if (GameUtility.mMap.mItemStone) {
            return 30;
        } else {
            return 10;
        }
    }

    @Override
    public float getWeight() {
        if (GameUtility.mMap.mItemStone) {
            return 4.0f;
        } else {
            return 5.0f;
        }
    }

}
