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
        if (!isDead) {
            batch.draw(texture, x, y, width, height);
        }
    }

    public void update(float deltaTime) {
        if (isDead) return;

        if (y <= ground.getHeight()) {
            y = (int) ground.getHeight();
            velocityY = 0;
            markAsDead();
            dispose();
        }

        if (!isDead) {
            y += velocityY * deltaTime;
        }

    }

    public boolean isInContactWithObstacle(BaseObstacle obstacle) {
        if (isDead) return false;
        return this.x < obstacle.getX() + obstacle.getWidth() &&
            this.x + this.width > obstacle.getX() &&
            this.y < obstacle.getY() + obstacle.getHeight() &&
            this.y + this.height > obstacle.getY();
    }
//if (isDead) return false;
//
//    float margin = 200.0f;
//
//    boolean inContact = this.x <= obstacle.getX() + obstacle.getWidth() + margin &&
//        this.x + this.width >= obstacle.getX() - margin &&
//        this.y <= obstacle.getY() + obstacle.getHeight() + margin &&
//        this.y + this.height >= obstacle.getY() - margin;
//
//        if (inContact) {
//        System.out.println("Pig near or in contact with obstacle. Margin: " + margin);
//        return true;
//    } else {
//        System.out.println("No near or actual contact detected. Pig: (" + x + ", " + y +
//            "), Obstacle: (" + obstacle.getX() + ", " + obstacle.getY() + ")");
//        return false;
//    }
    public void applyForce(float forceY) {
        if (isDead) return;

        this.velocityY = forceY;
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

    public Ground getGround() {
        return ground;
    }

    public void markAsDead() {
        if (!isDead) {
            System.out.println("Pig marked as dead at: " + getX() + ", " + getY());
            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void dispose() {
        if (isDead) return;
        texture.dispose();
    }

}
