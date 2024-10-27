package com.cantyouc.angrybirds.attack;

import com.cantyouc.angrybirds.bird.Bird;

public class Shot implements AttackType {
    private int x, y;
    private final int speed;
    private int initialAngle;
    private final int fullDamage;

    public Shot(int x, int y, int speed, int initialAngle, int fullDamage) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.initialAngle = initialAngle;
        this.fullDamage = fullDamage;
    }

    public void move() {
        // move bullet
    }

    public boolean isSpecial() {
        return false;
    }

    public void attack(Bird bird) {
        // attack bird
        // calculate distance from bird and pass to dealDamageTo()
    }

    public void dealDamageTo(Bird bird, int distanceFromBird) {
        // deal damage to bird
    }
}
