package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;

public class Tnt extends Item {

    public Tnt(float x, float y) {
        super(GameBitmap.TNT_0, x, y);
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
        return 1;
    }

    @Override
    public float getWeight() {
        return 2.5f;
    }

}
