package com.cantyouc.angrybirds.misc;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ground {
    private final float height; // Height of the ground
    private final int width; // Width of the ground
    private final float y; // Y position of the ground

    public Ground(int width) {
        this.width = width;
        this.height = 150; // Set a constant height for the ground
        this.y = 0; // Position at the bottom of the screen
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(0f, 0f, 0f, 0f);
        //transparent
        shapeRenderer.rect(0, y, width, height); // Draw a rectangle for the ground
    }

    public int getWidth() {
        return width;
    }

    public float getHeight() {
        return height; // Return the constant height
    }
}

//public class Ground {
//    private ArrayList<Double> heights;
//    private final int width;
//
//    public Ground(int width) {
//        this.width = width;
//        heights = new ArrayList<>();
//        for(int i = 0; i < width; i++) {
//            heights.add(0.0);
//        }
//        randomize();
//    }
//
//    public void randomize() {
//        Random rng = new Random();
//        double scalingFactor = ((double) Gdx.graphics.getHeight()) / 768f;
//
//        for (int i=0; i < 4; i++) {
//            double mean = 400f*scalingFactor - rng.nextInt(300);
//            double freq = 1f / (200 - rng.nextInt(100));
//            double amp = 100f*scalingFactor - rng.nextInt(90);
//            double shift = 10*rng.nextDouble();
//            for (int j=0; j < width; j++) {
//                heights.set(j, heights.get(j) + amp*Math.sin(j*freq + shift) + mean);
//            }
//        }
//        for (int j=0; j < width; j++) {
//            heights.set(j, heights.get(j)/4);
//        }
//    }
//
//    public void draw(ShapeRenderer shapeRenderer) {
//        for(int i = 0; i < width; i++) {
//            shapeRenderer.rect(i, 0, 1, heights.get(i).floatValue());
//        }
//    }
//
//    public int getWidth() {
//        return width;
//    }
//
//    public double getHeight(int x) {
//        return heights.get(x);
//    }
//}