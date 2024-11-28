package com.cantyouc.angrybirds.bird;

import com.cantyouc.angrybirds.misc.Ground;
import com.cantyouc.angrybirds.Obstacle;
import com.cantyouc.angrybirds.pig;
import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;

public class Black extends Bird {
    private boolean hasExploded = false;
    private float explosionRadius = 500f; // Explosion radius
    private float explosionPower = 500f;
    public Black(int x, int y, int height, int width, Ground ground, boolean flipped) {
        super(x, y, height, width, ground, flipped);
    }
    public void move(float deltaTime) throws BirdOutOfScreenException {
        super.move(deltaTime);
    }
    public boolean hasExploded() {
        return hasExploded;
    }
    public void explode(Obstacle[] obstacles, pig[] pigs) {
        if (!hasExploded) {
            hasExploded = true;
            for (Obstacle obstacle : obstacles) {
                float distance = calculateDistance(obstacle.getX(), obstacle.getY());
                if (distance <= explosionRadius) {
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
                    if (distance <= explosionRadius) {
                        pigs[i].markAsDead();
                    }
                }
            }
            setExhausted(true);
        } else {
            System.out.println("Black Bird has already exploded. Skipping repeat explosion.");
        }
    }
    private float calculateDistance(float targetX, float targetY) {
        return (float) Math.sqrt(
            Math.pow(targetX - getX(), 2) +
                Math.pow(targetY - getY(), 2)
        );
    }
}
