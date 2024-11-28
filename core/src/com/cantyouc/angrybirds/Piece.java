package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.cantyouc.angrybirds.bird.Bird;

public class Piece {
    private TextureRegion pieceTexture;
    private float x, y, width, height;
    private float xVelocity, yVelocity;

    public Piece(TextureRegion texture, float x, float y, float width, float height,
                 float xVelocity, float yVelocity) {
        this.pieceTexture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void update(float deltaTime) {
        this.x += xVelocity * deltaTime;
        this.y += yVelocity * deltaTime;
        this.yVelocity -= 9.8f * deltaTime;  // Apply gravity
    }

    public void draw(SpriteBatch batch) {
        batch.draw(pieceTexture, x, y, width, height);
    }
}
