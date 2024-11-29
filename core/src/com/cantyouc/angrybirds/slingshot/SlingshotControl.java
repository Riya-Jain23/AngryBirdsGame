package com.cantyouc.angrybirds.slingshot;

import com.badlogic.gdx.InputAdapter;
import com.cantyouc.angrybirds.bird.Bird;

public class SlingshotControl extends InputAdapter {
    private Bird bird;
    private Slingshot slingshot;
    private boolean isDragging;
    private float startX, startY;

    public SlingshotControl(Slingshot slingshot) {
        this.bird = bird;
        this.slingshot = slingshot;
        this.startX = slingshot.getX();
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
            bird.setPosition(screenX, screenY);
        }
        return true;
    }

    public boolean isDragging() {
        return isDragging;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDragging) {
            isDragging = false;
            launchBird(screenX, screenY);
        }
        return true;
    }

    private void launchBird(int screenX, int screenY) {
        float dx = startX - screenX;
        float dy = startY - screenY;

        int xVelocity = (int)(dx * 2);
        int yVelocity = (int)(dy * 2);

        bird.setXVelocity(xVelocity);
        bird.setYVelocity(yVelocity);
    }
}
