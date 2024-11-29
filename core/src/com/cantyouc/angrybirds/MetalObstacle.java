package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.cantyouc.angrybirds.bird.Bird;

public class MetalObstacle extends BaseObstacle {
    private boolean isCrumbling = false;
    private java.util.List<Piece> pieces = new java.util.ArrayList<>();
    private static final int MAX_HITS = 3;
    private int hitCount = 0;
    private float rotationAngle = 0;
    private float rotationThreshold = 45f;
    private boolean isFalling = false;
    private float fallVelocity = 0;
    private final float recoveryRate = 5f;
    private final float gravity = 500f;
    private final float friction = 50f;
    private float pivotX;
    private float pivotY;


    public MetalObstacle(float x, float y) {
        super(x, y, "metal_obstacle.png", 0.9f, 2.0f);
        this.pivotX = x + width / 2;
        this.pivotY = y + height;
    }

    @Override
    public void update(float deltaTime) {
        if (isFalling) {
            fallVelocity += gravity * deltaTime;
            y -= fallVelocity * deltaTime;
            x += xVelocity * deltaTime;
            xVelocity = Math.max(xVelocity - friction * deltaTime, 0);

            if (y + height < 0) {
                isCrumbling = true;
            }
        } else {
            if (rotationAngle > 0) {
                rotationAngle -= 7 * deltaTime;
                if (rotationAngle < 0) rotationAngle = 0;
            }
        }

        pivotX = x + width / 2;
        pivotY = y + height;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, pivotX - x, pivotY - y, width, height,
            1, 1, rotationAngle,
            0, 0, (int) width, (int) height,
            false, false);
    }

    @Override
    public void onCollision(Bird bird) {
        float impactForce = (float) Math.sqrt(
            bird.getXVelocity() * bird.getXVelocity() +
                bird.getYVelocity() * bird.getYVelocity()
        );

        if (impactForce > 15) {
            bend(impactForce / 10);
            xVelocity = bird.getXVelocity() * 0.5f;
            if (rotationAngle >= rotationThreshold) {
                isFalling = true;
                fallVelocity = 0;
            }
        }
    }

    @Override
    public void bend(float angle) {
        rotationAngle += 4 * angle;
        if (rotationAngle >= rotationThreshold) {
            isFalling = true;
            fallVelocity = 4;
        }
    }

    @Override
    public void crumble() {

    }

    public int getHitCount() {
        return hitCount;
    }
}
