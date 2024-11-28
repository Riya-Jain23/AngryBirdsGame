package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.cantyouc.angrybirds.bird.Bird;

public class WoodenObstacle extends BaseObstacle {
    private boolean isCrumbling = false;
    private java.util.List<Piece> pieces = new java.util.ArrayList<>();
    private int hitCount = 0;

    public WoodenObstacle(float x, float y) {
        super(x, y, "wooden_obstacle.png", 0.6f, 1.0f);
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
        float impactForce = Math.abs(bird.getXVelocity()) + Math.abs(bird.getYVelocity());

        if (impactForce > 15) {
            hitCount++;

            if (hitCount >= 2) {
                crumble();
            }
        }
    }

    public void crumble() {
        isCrumbling = true;
        pieces.clear();

        int numPieces = 15;
        for (int i = 0; i < numPieces; i++) {
            float randomX = MathUtils.random(0, width - 20);
            float randomY = MathUtils.random(0, height - 20);
            float randomWidth = MathUtils.random(10, 40);
            float randomHeight = MathUtils.random(10, 40);

            TextureRegion region = new TextureRegion(texture,
                (int) randomX, (int) randomY, (int) randomWidth, (int) randomHeight);

            Piece piece = new Piece(region, x + randomX, y + randomY,
                randomWidth, randomHeight,
                MathUtils.random(-30, 30), MathUtils.random(-30, 30));
            pieces.add(piece);
        }
    }
}
