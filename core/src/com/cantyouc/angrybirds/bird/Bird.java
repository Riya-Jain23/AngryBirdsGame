package com.cantyouc.angrybirds.bird;

import com.cantyouc.angrybirds.attack.AttackType;
import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.cantyouc.angrybirds.misc.Ground;

import java.lang.Math;

public class Bird {
    public int x;
    public int y;
    private final boolean flipped;
    public final int height;
    public final int width;
    private final Rectangle hitbox;
    private final Ground ground;
    protected float xVelocity, yVelocity;
    protected AttackType defaultAttack;
    protected TextureRegion image;
    protected int maxAttackPower;
    private int attackAngle;
    private boolean exhausted;

    public Bird(int x, int y,int height, int width, Ground ground, boolean flipped) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.ground = ground;
        this.flipped = flipped;
        hitbox = new Rectangle(x, y, width, height);
    }
    public void setImage(TextureRegion image) {
        this.image = image;
        this.image.flip(this.flipped, false);
    }
    public void setX(int x) {
        this.x = x;
    }
    public TextureRegion getImage() {
        return image;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, x, y, width / 2f, height / 2f, width, height, 1, 1, getAngle());
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public boolean isExhausted() {
        return exhausted;
    }

    public void setExhausted(boolean exhausted) {
        this.exhausted = exhausted;
    }

    public void launch(float angle, float power) {
        float angleRad = (float) Math.toRadians(angle);
        xVelocity = power * (float) Math.cos(angleRad);
        yVelocity = power * (float) Math.sin(angleRad);
    }
    public void move(float deltaTime) throws BirdOutOfScreenException {
        if (x + xVelocity > 1 && x + xVelocity < ground.getWidth() - width - 1) {
            x += xVelocity;}
        x += xVelocity * deltaTime;
        if (y + yVelocity > 1 && y + yVelocity < ground.getWidth() - width - 1) {
            y += yVelocity;}
        yVelocity -= Ground.GRAVITY * deltaTime;
        y += yVelocity * deltaTime;

        if (y <= ground.getHeight()) {
            y = (int) ground.getHeight();
            yVelocity = 0;
            xVelocity = 0;
        }

        if (x < 0 || x > ground.getWidth() || y > ground.getHeight() + 600) {
            throw new BirdOutOfScreenException("Bird is out of screen bounds.");
        }
        setExhausted(true);
        hitbox.setPosition(x, y);
    }

    public void setXVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getAngle() {
        return (float) Math.toDegrees(Math.atan2(yVelocity, xVelocity));
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setAttackAngle(int attackAngle) {
        this.attackAngle = attackAngle;
    }

    public int getAttackAngle() {
        return attackAngle;
    }

    public void setPosition(float v, float v1) {
        this.x = Math.round(v);
        this.y = Math.round(v1);
    }

    public void setYVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public float getXVelocity() {
        return xVelocity;
    }

    public float getYVelocity() {
        return yVelocity;
    }
}
