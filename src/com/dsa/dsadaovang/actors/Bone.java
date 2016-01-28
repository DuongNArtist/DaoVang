package com.dsa.dsadaovang.actors;

import com.dsa.dsadaovang.engines.GameBitmap;

public class Bone extends Item {

    public Bone(float x, float y) {
        super(GameBitmap.BONE_0, x, y);
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
        return 2.0f;
    }

}
