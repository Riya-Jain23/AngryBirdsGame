package com.cantyouc.angrybirds.menu;

import com.cantyouc.angrybirds.WoodenObstacle;
import com.cantyouc.angrybirds.bird.Bird;
import com.cantyouc.angrybirds.misc.AngryBirds;
import com.cantyouc.angrybirds.misc.Ground;
import com.cantyouc.angrybirds.misc.MainScreen;
import com.cantyouc.angrybirds.pig;
import com.cantyouc.angrybirds.BaseObstacle;
import com.cantyouc.angrybirds.Slingshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private final List<BirdState> birds;
    private final List<PigState> pigs;
    private final List<SerializedObstacle> obstacles;
    private final Slingshot slingshot;
    private final MainScreen currentBirdIndex;
    private final boolean birdLaunched;

    public GameState(AngryBirds game) {
        // Save birds' states
        this.birds = new ArrayList<>();
        for (Bird bird : game.getBirds()) {
            birds.add(new BirdState(
                bird.getX(),
                bird.getY(),
                bird.getXVelocity(),
                bird.getYVelocity(),
                bird.isExhausted()
            ));
        }

        // Save pigs' states
        this.pigs = new ArrayList<>();
        for (pig pig : game.getPigs()) {
            pigs.add(new PigState(pig));
        }

        // Save obstacles' states
        this.obstacles = new ArrayList<>();
        for (BaseObstacle obstacle : game.getObstacles()) {
            obstacles.add(new SerializedObstacle(obstacle));
        }

        // Save slingshot state
        this.slingshot = game.getSlingshot();

        // Save game-specific state variables
        this.currentBirdIndex = game.getCurrentBirdIndex();
        this.birdLaunched = game.isBirdLaunched();
    }

    // Reconstruct birds from their saved states
    public Bird[] getBirds(Ground ground) {
        List<Bird> birdList = new ArrayList<>();
        for (BirdState state : birds) {
            Bird bird = new Bird(state.x, state.y, state.height, state.width, ground, false);
            bird.setXVelocity(state.xVelocity);
            bird.setYVelocity(state.yVelocity);
            bird.setExhausted(state.exhausted);
            birdList.add(bird);
        }
        return birdList.toArray(new Bird[0]);
    }

    // Reconstruct pigs from their saved states
    public pig[] getPigs() {
        List<pig> pigList = new ArrayList<>();
        for (PigState state : pigs) {
            pigList.add(new pig(state.x, state.y, state.scale, state.ground, state.path));
        }
        return pigList.toArray(new pig[0]);
    }

    // Reconstruct obstacles from their saved states
    public BaseObstacle[] getObstacles() {
        List<BaseObstacle> obstacleList = new ArrayList<>();
        for (SerializedObstacle state : obstacles) {
            obstacleList.add(new WoodenObstacle(state.x, state.y));
        }
        return obstacleList.toArray(new BaseObstacle[0]);
    }

    public Slingshot getSlingshot() {
        return slingshot;
    }


    public MainScreen getCurrentBirdIndex() {
        return currentBirdIndex;
    }

    public boolean isBirdLaunched() {
        return birdLaunched;
    }

    public List<BaseObstacle> recreateObstacles() {
        List<BaseObstacle> recreatedObstacles = new ArrayList<>();
        for (SerializedObstacle serializedObstacle : obstacles) {
            recreatedObstacles.add(serializedObstacle.toObstacle());
        }
        return recreatedObstacles;
    }

    // Nested state classes for birds, pigs, and obstacles
    private static class BirdState implements Serializable {
        private final int x, y;
        private final float xVelocity, yVelocity;
        private final boolean exhausted;
        private final int height = 70, width = 70;  // Default bird size

        public BirdState(int x, int y, float xVelocity, float yVelocity, boolean exhausted) {
            this.x = x;
            this.y = y;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
            this.exhausted = exhausted;
        }
    }

    private static class PigState implements Serializable {
        private float x, y;
        private float scale;
        private Ground ground;
        private String path;

        public PigState(pig pig) {
            this.x = pig.getX();
            this.y = pig.getY();
            this.ground = pig.getGround();
        }
    }

    public static class SerializedObstacle implements Serializable {
        private final float x, y, width, height, xVelocity, yVelocity;
        private final boolean isCrumbling;
        public String path;

        public SerializedObstacle(BaseObstacle obstacle) {
            this.x = obstacle.getX();
            this.y = obstacle.getY();
            this.width = obstacle.getWidth();
            this.height = obstacle.getHeight();
            this.xVelocity = obstacle.getxVelocity(); // Use actual velocity
            this.yVelocity = obstacle.getyVelocity();
            this.isCrumbling = obstacle.isCrumbling(); // Add logic if needed to handle crumbling state
        }

        public BaseObstacle toObstacle() {
            BaseObstacle obstacle = new WoodenObstacle(x, y);
            obstacle.push(xVelocity, yVelocity); // Restore movement state if necessary
            return obstacle;
        }
    }
}

