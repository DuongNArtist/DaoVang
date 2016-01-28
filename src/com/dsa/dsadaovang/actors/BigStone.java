package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameUtility;

public class BigStone extends Item {

    public BigStone(float x, float y) {
        super(GameBitmap.STONE_BIG_0, x, y);
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
            return 50;
        } else {
            return 20;
        }
    }

    @Override
    public float getWeight() {
        if (GameUtility.mMap.mItemStone) {
            return 5.0f;
        } else {
            return 6.0f;
        }
    }
}
