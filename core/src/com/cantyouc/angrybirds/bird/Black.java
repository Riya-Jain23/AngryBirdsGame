package com.cantyouc.angrybirds.bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cantyouc.angrybirds.BaseObstacle;
import com.cantyouc.angrybirds.misc.Ground;
import com.cantyouc.angrybirds.BaseObstacle;
import com.cantyouc.angrybirds.pig;
import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;

public class Black extends Bird {
    private boolean hasExploded = false;
    private float explosionRadius = 400f;
    private float explosionPower = 500f;
    private ParticleEffect smokeEffect;
    private boolean triggerExplosion = false;
    private Texture overlayTexture;
    private boolean spacebarPressed;

    public Black(int x, int y, int height, int width, Ground ground, boolean flipped) {
        super(x, y, height, width, ground, flipped);
        smokeEffect = new ParticleEffect();
        smokeEffect.load(
            Gdx.files.internal("smoke effect.p"),
            Gdx.files.internal("effects")
        );
    }
    public void triggerExplosion() {
        this.triggerExplosion = true;
    }
    public void move(float deltaTime) throws BirdOutOfScreenException {
        super.move(deltaTime);
        // Update smoke effect if active
        if (smokeEffect.isComplete()) return;
        smokeEffect.setPosition(getX(), getY());
        smokeEffect.update(deltaTime);
    }
    public boolean hasExploded() {
        return hasExploded;
    }
    public void explode(BaseObstacle[] obstacles, pig[] pigs) {
        if (!hasExploded) {
            hasExploded = true;
            smokeEffect.setPosition(getX(), getY());
            smokeEffect.start();
            for (BaseObstacle obstacle : obstacles) {
                float distance = calculateDistance(obstacle.getX(), obstacle.getY());
                if (distance < explosionRadius) {
                    float pushForce = explosionPower * (1 - (distance / explosionRadius));
                    obstacle.push(
                        Math.signum(obstacle.getX() - getX()) * pushForce,
                        Math.signum(obstacle.getY() - getY()) * pushForce
                    );
                    obstacle.crumble();
                }
            }
            for (int i = 0; i < pigs.length; i++) {
                if (pigs[i] != null) {
                    float distance = calculateDistance(pigs[i].getX(), pigs[i].getY());
                    if (distance < explosionRadius) {
                        pigs[i].markAsDead();
                    }
                }
            }
            setExhausted(true);
        } else {
            System.out.println("Black Bird has already exploded. Skipping repeat explosion.");
        }
    }

    public void renderExplosion(SpriteBatch batch) {
        if (!smokeEffect.isComplete()) {
            smokeEffect.draw(batch);
        }
    }

    public void setSpacebarPressed(boolean pressed) {
        this.spacebarPressed = pressed;
    }

    public boolean isSpacebarPressed() {
        return spacebarPressed;
    }


    private float calculateDistance(float targetX, float targetY) {
        return (float) Math.sqrt(
            Math.pow(targetX - getX(), 2) +
                Math.pow(targetY - getY(), 2)
        );
    }

    // Reset explosion flag after triggering explosion
    public void resetExplosionFlag() {
        this.triggerExplosion = false;
    }

    public boolean shouldTriggerExplosion() {
        return this.triggerExplosion;
    }
}
