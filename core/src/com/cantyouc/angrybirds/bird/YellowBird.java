package com.cantyouc.angrybirds.bird;

import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;
import com.cantyouc.angrybirds.misc.Ground;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.lang.Math;

public class YellowBird extends Bird {

    private static final float SPEED_MULTIPLIER = 1.03f;

    public YellowBird(int x, int y, int height, int width, Ground ground, boolean flipped) {
        super(x, y, height, width, ground, flipped);
    }

    @Override
    public void move(float deltaTime) throws BirdOutOfScreenException {
        xVelocity *= SPEED_MULTIPLIER;
        yVelocity *= SPEED_MULTIPLIER;
        super.move(deltaTime);
    }


}
