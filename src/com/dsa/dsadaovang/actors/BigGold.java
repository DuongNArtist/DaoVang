package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;

public class BigGold extends Item {

    public BigGold(float x, float y) {
        super(GameBitmap.GOLD_BIG_0, x, y);
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
        return 500;
    }

    @Override
    public float getWeight() {
        return 6.0f;
    }

}
