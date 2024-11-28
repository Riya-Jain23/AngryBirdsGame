package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.cantyouc.angrybirds.bird.Bird;

public class MetalObstacle extends BaseObstacle {
    private boolean isCrumbling = false;
    private java.util.List<Piece> pieces = new java.util.ArrayList<>();
    private static final int MAX_HITS = 3; // Set max hits to 3 for crumbling
    private int hitCount = 0; // Track the number of hits
    private float rotationAngle = 0; // Current rotation angle for bending
    private float rotationThreshold = 45f; // Maximum angle before falling
    private boolean isFalling = false; // Whether the obstacle is falling
    private float fallVelocity = 0; // Vertical velocity for falling
    private final float recoveryRate = 5f; // Rate of recovery to upright position
    private final float gravity = 500f; // Gravity to simulate falling
    private final float friction = 50f; // Friction to slow horizontal movement
    private float pivotX; // Pivot point for rotation
    private float pivotY;


    public MetalObstacle(float x, float y) {
        super(x, y, "metal_obstacle.png", 0.9f, 2.0f);
        this.pivotX = x + width / 2; // Center of the obstacle
        this.pivotY = y + height; // Top edge of the obstacle
    }

    @Override
    public void update(float deltaTime) {
        if (isFalling) {
            // Update position and velocity for falling motion
            fallVelocity += gravity * deltaTime; // Accelerate downwards
            y -= fallVelocity * deltaTime; // Move down
            x += xVelocity * deltaTime; // Move horizontally
            xVelocity = Math.max(xVelocity - friction * deltaTime, 0); // Apply friction to slow horizontal motion

            // Stop falling once the obstacle is out of bounds
            if (y + height < 0) {
                isCrumbling = true; // Mark as "destroyed" for removal
            }
        } else {
            // Recovery logic: gradually return to upright position
            if (rotationAngle > 0) {
                rotationAngle -= 7 * deltaTime; // Gradual recovery
                if (rotationAngle < 0) rotationAngle = 0; // Avoid overcorrection
            }
        }

        // Update the pivot point for rotation
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
        // Calculate the impact force of the bird's collision
        float impactForce = (float) Math.sqrt(
            bird.getXVelocity() * bird.getXVelocity() +
                bird.getYVelocity() * bird.getYVelocity()
        );

        if (impactForce > 15) {
            bend(impactForce / 10); // Bend based on impact strength
            xVelocity = bird.getXVelocity() * 0.5f; // Set horizontal motion based on bird's velocity
            if (rotationAngle >= rotationThreshold) {
                isFalling = true; // Trigger falling if threshold is exceeded
                fallVelocity = 0; // Reset fall velocity for realistic falling
            }
        }
    }

    @Override
    public void bend(float angle) {
        rotationAngle += 4 * angle;
        if (rotationAngle >= rotationThreshold) {
            // Trigger falling once the angle exceeds the threshold
            isFalling = true;
            fallVelocity = 4; // Start falling
        }
    }

    @Override
    public void crumble() {

    }

    public int getHitCount() {
        return hitCount;
    }
}
