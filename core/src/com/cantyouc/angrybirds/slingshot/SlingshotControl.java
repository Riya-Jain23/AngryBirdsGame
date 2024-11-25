package com.cantyouc.angrybirds.slingshot;

import com.badlogic.gdx.InputAdapter;
import com.cantyouc.angrybirds.bird.Bird;

public class SlingshotControl extends InputAdapter {
    private Bird bird;
    private Slingshot slingshot;
    private boolean isDragging;  // Make this non-static to manage per instance
    private float startX, startY;

    public SlingshotControl(Slingshot slingshot) {
        this.bird = bird;
        this.slingshot = slingshot;
        this.startX = slingshot.getX();  // Start point based on slingshot’s position
        this.startY = slingshot.getY();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //if (bird.isTouching(screenX, screenY)) {
         //   isDragging = true;  // Start dragging
         //   return true;
        //}
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            bird.setPosition(screenX, screenY);  // Move bird to follow the drag
        }
        return true;
    }

    public boolean isDragging() {
        return isDragging;  // Return the correct dragging state
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDragging) {
            isDragging = false;  // Stop dragging
            launchBird(screenX, screenY);  // Launch bird on release
        }
        return true;
    }

    private void launchBird(int screenX, int screenY) {
        float dx = startX - screenX;
        float dy = startY - screenY;

        int xVelocity = (int)(dx * 2);  // Adjust multipliers for launch speed
        int yVelocity = (int)(dy * 2);

        bird.setXVelocity(xVelocity);
        bird.setYVelocity(yVelocity);
    }
}
