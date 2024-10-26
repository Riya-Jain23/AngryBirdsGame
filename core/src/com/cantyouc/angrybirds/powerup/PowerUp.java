package com.cantyouc.angrybirds.powerup;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cantyouc.angrybirds.bird.Bird;

public interface PowerUp {
    public TextureRegion getImage();
    public void applyPowerUpTo(Bird bird);
}
