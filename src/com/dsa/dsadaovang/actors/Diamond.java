package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameUtility;

public class Diamond extends Item {

    public Diamond(float x, float y) {
        super(GameBitmap.DIAMOND_0, x, y);
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
        if (GameUtility.mMap.mItemDiamond) {
            return 900;
        } else {
            return 600;
        }
    }

    @Override
    public float getWeight() {
        if (GameUtility.mMap.mItemDiamond) {
            return 2.0f;
        } else {
            return 2.5f;
        }
    }

}
