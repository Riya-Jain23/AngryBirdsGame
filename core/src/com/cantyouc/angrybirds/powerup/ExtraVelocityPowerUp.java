package com.cantyouc.angrybirds.powerup;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cantyouc.angrybirds.bird.Bird;

public class ExtraVelocityPowerUp implements PowerUp {
    private final int velocityBonus;
    private TextureRegion image;

    public ExtraVelocityPowerUp(int velocityBonus) {
        this.velocityBonus = velocityBonus;
    }

    public int getVelocityBonus() {
        return velocityBonus;
    }

    @Override
    public TextureRegion getImage() {
        return image;
    }

    @Override
    public void applyPowerUpTo(Bird bird) {
//        bird.setVelocity(bird.getVelocity() + velocityBonus);
    }
}
