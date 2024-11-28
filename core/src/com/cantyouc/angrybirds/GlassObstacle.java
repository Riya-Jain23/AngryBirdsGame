package com.cantyouc.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.cantyouc.angrybirds.bird.Bird;

public class GlassObstacle extends BaseObstacle {
    private boolean isCrumbling = false;
    private java.util.List<Piece> pieces = new java.util.ArrayList<>();
    private boolean isBroken = false;
    private float xVelocity, yVelocity;

    public GlassObstacle(float x, float y) {
        super(x, y, "glass_obstacle.png", 0.3f, 0.5f);
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
        crumble();

    }

    public void crumble() {
        isCrumbling = true;
        pieces.clear();

        int numPieces = 45;
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
        Gdx.audio.newSound(Gdx.files.internal("glassshatter.mp3")).play();
    }
}
