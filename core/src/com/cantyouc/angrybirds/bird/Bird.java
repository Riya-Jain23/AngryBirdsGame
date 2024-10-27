package com.cantyouc.angrybirds.bird;

import com.cantyouc.angrybirds.attack.AttackType;
import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.cantyouc.angrybirds.misc.Ground;

import java.lang.Math;

public class Bird {
    private int x, y;
    private final boolean flipped;
    private final int height, width;
    private final Rectangle hitbox;
    private final Ground ground;
    protected int xVelocity, yVelocity;
    protected AttackType defaultAttack;
    protected TextureRegion image;
    protected int maxAttackPower;
    private int attackAngle;

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
    public TextureRegion getImage() {
        return image;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(image, x, getY(), width/2f, height/2f, width, height, 1, 1, getAngle());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return ((int) ground.getHeight());
    }

    public void move() throws BirdOutOfScreenException {
        if (x+xVelocity > 1 && x+xVelocity < ground.getWidth()-width-1) {
            x += xVelocity;
        } else {
            throw new BirdOutOfScreenException("Bird is out of screen");
        }
        x += xVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getAngle() {
        return ((float) Math.atan((ground.getHeight() - ground.getHeight())/width) * 180 / (float) Math.PI);
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

}
