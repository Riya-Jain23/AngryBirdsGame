package com.cantyouc.angrybirds;

import com.badlogic.gdx.math.Rectangle;
import com.cantyouc.angrybirds.misc.Ground;
import com.cantyouc.angrybirds.misc.MainScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class pig {
    private Texture texture;
    private float x, y;
    private float width, height;
    private float velocityY = 0;
    private boolean isDead = false;
    private final Rectangle hitbox;
    private final Ground ground;


    public pig(float x, float y, float scale, Ground ground, String path) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(path);
        this.width = texture.getWidth() * scale;
        this.height = texture.getHeight() * scale;
        this.ground = ground;
        hitbox = new Rectangle(x, y, width, height);
    }

    public void draw(SpriteBatch batch) {
        if (!isDead) { // Only draw if the pig is not dead
            batch.draw(texture, x, y, width, height);
        }
    }

    public void update(float deltaTime) {
        // Apply gravity to the pig if it's falling
        if (isDead) return;

        // Ground collision check (the bird should stop when it hits the ground)
        if (y <= ground.getHeight()) {
            y = (int) ground.getHeight();  // Snap to ground level
            velocityY = 0;  // Stop vertical motion
            markAsDead();
            dispose();
        }

        if (!isDead) {
            y += velocityY * deltaTime;
        }

    }

    // Check if the pig is in contact with the obstacle
    public boolean isInContactWithObstacle(Obstacle obstacle) {
        if (isDead) return false;
        return this.x < obstacle.getX() + obstacle.getWidth() &&
            this.x + this.width > obstacle.getX() &&
            this.y < obstacle.getY() + obstacle.getHeight() &&
            this.y + this.height > obstacle.getY();
    }

    public void applyForce(float forceY) {
        if (isDead) return; // Do not apply force if the pig is dead

        this.velocityY = forceY; // Apply a force (like pushing the pig off the obstacle)
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

    public void markAsDead() {
        System.out.println("Pig marked as dead at: " + getX() + ", " + getY());
        isDead = true; // Mark the pig as dead
    }

    public boolean isDead() {
        return isDead;
    }

    public void dispose() {
        if (isDead) return;
        texture.dispose(); // Clean up texture
    }
}
