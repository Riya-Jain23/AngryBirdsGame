package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Alien {
    private Texture texture;
    private float x, y;
    private float width, height;


    public Alien(float x, float y, float scale) {
        this.x = x;
        this.y = y;
        this.texture = new Texture("pig1.png"); // Replace with your alien texture
        this.width = texture.getWidth() * scale;
        this.height = texture.getHeight() * scale;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
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
