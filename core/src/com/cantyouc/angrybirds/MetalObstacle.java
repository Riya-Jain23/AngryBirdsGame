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

    public MetalObstacle(float x, float y) {
        super(x, y, "metal_obstacle.png", 0.9f, 2.0f);
    }

    @Override
    public void update(float deltaTime) {
        if (isCrumbling) {
            for (Piece piece : pieces) {
                piece.update(deltaTime);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!isCrumbling) {
            batch.draw(texture, x, y);
        } else {
            for (Piece piece : pieces) {
                piece.draw(batch);
            }
        }
    }

    @Override
    public void onCollision(Bird bird) {
        // Calculate the impact force of the bird's collision
        float impactForce = (float) Math.sqrt(
            bird.getXVelocity() * bird.getXVelocity() +
                bird.getYVelocity() * bird.getYVelocity()
        );

        // Only increase hit count if the impact is strong enough
        if (impactForce > 15) {
            hitCount++; // Increment the hit count when a collision occurs

            // Crumble after the third hit only
            if (hitCount == MAX_HITS && !isCrumbling) {
                crumble(); // Trigger crumbling on the third hit
            }
        }
    }

    @Override
    public void crumble() {
        isCrumbling = true;
        pieces.clear();

        int numPieces = 10; // Number of pieces to break the obstacle into
        for (int i = 0; i < numPieces; i++) {
            float randomX = MathUtils.random(0, width - 30);
            float randomY = MathUtils.random(0, height - 30);
            float randomWidth = MathUtils.random(20, 50);
            float randomHeight = MathUtils.random(20, 50);

            TextureRegion region = new TextureRegion(texture,
                (int) randomX, (int) randomY, (int) randomWidth, (int) randomHeight);

            Piece piece = new Piece(region, x + randomX, y + randomY,
                randomWidth, randomHeight,
                MathUtils.random(-20, 20), MathUtils.random(-20, 20));
            pieces.add(piece);
        }
    }

    public int getHitCount() {
        return hitCount;
    }
}
