package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle {
    private Texture texture;
    private float x, y;
    private float width, height;

    public Obstacle(float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture("obstacle1.png"); // Replace with your obstacle texture
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void dispose() {
        texture.dispose(); // Clean up texture
    }
}
