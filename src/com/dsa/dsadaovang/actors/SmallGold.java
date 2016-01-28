package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;

public class SmallGold extends Item {

    public SmallGold(float x, float y) {
        super(GameBitmap.GOLD_SMALL_0, x, y);
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
        return 100;
    }

    @Override
    public float getWeight() {
        return 3.5f;
    }
}
