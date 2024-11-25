package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle {
    private Texture texture;  // This resolves the issue with the texture symbol
    private float x, y;
    private float width, height;
    private float xVelocity, yVelocity; // For pushing the obstacle

    public Obstacle(float x, float y, String path) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(path);  // Loads the texture
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.xVelocity = 0;
        this.yVelocity = 0;
    }

    // Update position based on velocity
    public void update(float deltaTime) {
        this.x += xVelocity * deltaTime;
        this.y += yVelocity * deltaTime;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    // Set new velocity when the obstacle is pushed
    public void push(float vx, float vy) {
        this.xVelocity = vx;
        this.yVelocity = vy;
    }

    // Getters for collision detection
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    // Dispose texture when done
    public void dispose() {
        texture.dispose();
    }
}
