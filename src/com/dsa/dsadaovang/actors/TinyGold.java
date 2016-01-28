package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;

public class TinyGold extends Item {

    public TinyGold(float x, float y) {
        super(GameBitmap.GOLD_TINY_0, x, y);
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
        return 50;
    }

    @Override
    public float getWeight() {
        return 2.5f;
    }

}
