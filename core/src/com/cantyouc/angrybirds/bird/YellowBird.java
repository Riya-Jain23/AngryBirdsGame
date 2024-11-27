package com.cantyouc.angrybirds.bird;

import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;
import com.cantyouc.angrybirds.misc.Ground;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.lang.Math;

public class YellowBird extends Bird {

    private static final float SPEED_MULTIPLIER = 1.03f; // Higher speed multiplier for YellowBird

    public YellowBird(int x, int y, int height, int width, Ground ground, boolean flipped) {
        super(x, y, height, width, ground, flipped);
    }

    @Override
    public void move(float deltaTime) throws BirdOutOfScreenException {
        // Apply speed multiplier to the velocity
        xVelocity *= SPEED_MULTIPLIER;
        yVelocity *= SPEED_MULTIPLIER;

        // Now call the move method of the base class (Bird)
        super.move(deltaTime);
    }


}
