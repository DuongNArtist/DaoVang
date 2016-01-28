package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;

public class MineBag extends Item {

    public MineBag(float x, float y) {
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
        return 0;
    }

    @Override
    public float getWeight() {
        return 2.5f;
    }

}
