package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;

public class Skull extends Item {

    public Skull(float x, float y) {
        super(GameBitmap.SKULL_0, x, y);
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
        return 2;
    }

    @Override
    public float getWeight() {
        return 2.5f;
    }

}
