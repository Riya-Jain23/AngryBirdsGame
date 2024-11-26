package com.cantyouc.angrybirds.misc;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ground {
    public static final float GRAVITY = 9.8f;
    private final float height;
    private final int width;
    private final float y;

    public Ground(int width) {
        this.width = width;
        this.height = 70;
        this.y = 0;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0f, 0f, 0f, 0f);
        shapeRenderer.rect(0, y, width, height);
    }

    public int getWidth() {
        return width;
    }

    public float getHeight() {
        return height;

    }
}
