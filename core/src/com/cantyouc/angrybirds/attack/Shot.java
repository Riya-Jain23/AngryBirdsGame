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

    }

    public boolean isSpecial() {
        return false;
    }

    public void attack(Bird bird) {

    }

    public void dealDamageTo(Bird bird, int distanceFromBird) {

    }
}
