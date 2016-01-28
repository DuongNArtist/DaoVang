package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;

public class MediumGold extends Item {

    public MediumGold(float x, float y) {
        super(GameBitmap.GOLD_MEDIUM_0, x, y);
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
        return 250;
    }

    @Override
    public float getWeight() {
        return 4.5f;
    }

}
