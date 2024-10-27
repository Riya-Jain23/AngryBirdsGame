package com.cantyouc.angrybirds.attack;

import com.cantyouc.angrybirds.bird.Bird;

public interface AttackType {
    public void attack(Bird bird);
    public boolean isSpecial();
    public void move();
    public void dealDamageTo(Bird bird, int distanceFromBird);
}
