package com.cantyouc.angrybirds.bird;

import com.cantyouc.angrybirds.attack.AttackType;
import com.cantyouc.angrybirds.misc.Ground;

public class Red extends Bird {

    protected AttackType defaultAttack;
    protected int maxAttackPower;


    public Red(int x, int y, int height, int width, Ground ground, boolean flipped) {
        super(x, y, height, width, ground, flipped);
    }
}
