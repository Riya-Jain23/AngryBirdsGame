package com.badlogic.angrybirds;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
class Bird {
    private int x, y, height, width;
    private Rectangle hitbox;
    Bird(int x,int y,int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        hitbox = new Rectangle(x, y, width, height);
    }
    void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(x, y, width, height);
    }
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }
    int getHeight() {
        return height;
    }
    int getWidth() {
        return width;
    }
}
