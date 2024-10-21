package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class But {
    private Texture texture;
    private Texture hoverTexture;
    private Rectangle bounds;
    private boolean isHovered;

    public But(String texturePath, float x, float y, float width, float height) {
        texture = new Texture(texturePath);
        String hoverTexturePath = null;
        hoverTexture = new Texture(hoverTexturePath);
        bounds = new Rectangle(x, y, width, height);
        isHovered = false;
    }

    public void render(SpriteBatch batch) {
        if (isHovered) {
            batch.draw(hoverTexture, bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public void checkHover(float mouseX, float mouseY) {
        isHovered = bounds.contains(mouseX, mouseY);
    }

    public boolean isClicked(float touchX, float touchY) {
        return bounds.contains(touchX, touchY);
    }

    public void dispose() {
        texture.dispose();
        hoverTexture.dispose();
    }
}
