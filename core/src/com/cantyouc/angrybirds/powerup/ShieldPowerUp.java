package com.cantyouc.angrybirds.powerup;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cantyouc.angrybirds.bird.Bird;

public class ShieldPowerUp implements PowerUp {
    private final int shieldBonus;
    private TextureRegion image;

    public ShieldPowerUp(int shieldBonus) {
        this.shieldBonus = shieldBonus;
    }

    public int getShieldBonus() {
        return shieldBonus;
    }

    @Override
    public TextureRegion getImage() {
        return image;
    }

    @Override
    public void applyPowerUpTo(Bird bird) {

    }
}
