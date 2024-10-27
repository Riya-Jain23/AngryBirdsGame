package com.cantyouc.angrybirds.attack;

import com.cantyouc.angrybirds.bird.Bird;

public class SpecialAttack implements AttackType {
    private int x, y;
    private final int speed;
    private int initialAngle;
    private final int fullDamage;

    public SpecialAttack(int x, int y, int speed, int initialAngle, int fullDamage) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.initialAngle = initialAngle;
        this.fullDamage = fullDamage;
    }

    public void move() {
        // move the attack if needed
        // this will not be required if the special attack directly blasts at the opposing bird's position
    }

    public boolean isSpecial() {
        return true;
    }

    public void attack(Bird bird) {
        // attack bird
        // calculate distance from bird and pass to dealDamageTo()
    }

    public void dealDamageTo(Bird bird, int distanceFromBird) {
        // deal damage to bird based on distance or otherwise
    }
}
