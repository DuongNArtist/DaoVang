package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;
import com.dsa.dsadaovang.engines.GameUtility;

public class GoldBag extends Item {

    public GoldBag(float x, float y) {
        super(GameBitmap.BAG_0, x, y);
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
        if (GameUtility.mMap.mItemLucky) {
            return 500 + (int) (300 * Math.random());
        } else {
            return 50 + (int) (300 * Math.random());
        }
    }

    @Override
    public float getWeight() {
        if (GameUtility.mMap.mItemLucky) {
            return 3.5f;
        } else {
            return 5.5f;
        }
    }

}
