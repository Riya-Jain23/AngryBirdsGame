package com.cantyouc.angrybirds.slingshot;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Trajectory {
    private ArrayList<Vector2> points;

    public Trajectory() {
        points = new ArrayList<>();
    }

    public void calculatePath(float startX, float startY, float xVelocity, float yVelocity, float gravity) {
        points.clear();
        float timeStep = 0.1f;
        float currentTime = 0f;

        for (int i = 0; i < 20; i++) {
            float x = startX + xVelocity * currentTime;
            float y = startY + yVelocity * currentTime - 0.5f * gravity * currentTime * currentTime;
            points.add(new Vector2(x, y));
            currentTime += timeStep;
        }
    }

    public ArrayList<Vector2> getPoints() {
        return points;
    }
}
