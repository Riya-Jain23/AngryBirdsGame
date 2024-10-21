package com.badlogic.angrybirds;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
class Ground {
    private ArrayList<Double> heights;
    private int width;
    Ground(int width) {
        this.width = width;
        heights = new ArrayList<Double>();
        for(int i = 0; i < width; i++) {
            heights.add(20.0);
        }
    }
    void draw(ShapeRenderer shapeRenderer) {
        for(int i = 0; i < width; i++) {
            shapeRenderer.rect(i, 0, 1, heights.get(i).floatValue());
        }
    }
}
