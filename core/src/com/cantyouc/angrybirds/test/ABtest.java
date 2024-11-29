package com.cantyouc.angrybirds.test;

import com.badlogic.gdx.Gdx;
import com.cantyouc.angrybirds.BaseObstacle;
import com.cantyouc.angrybirds.Slingshot;
import com.cantyouc.angrybirds.bird.Bird;
import com.cantyouc.angrybirds.bird.YellowBird;
import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;
import com.cantyouc.angrybirds.menu.FailMenu;
import com.cantyouc.angrybirds.menu.VictoryMenu;
import com.cantyouc.angrybirds.misc.AngryBirds;
import com.cantyouc.angrybirds.misc.Ground;
import com.cantyouc.angrybirds.misc.MainScreen;
import com.cantyouc.angrybirds.pig;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ABtest {

    private static final int TIMEOUT = 5000;
    private static Ground ground;
    private static YellowBird yellowbird;

    @Before
    public void setUp() {
        ground = new Ground(1600);
        yellowbird = new YellowBird(100, 150, 80, 70, ground, false);
    }

    @Test
    public void testBirdOutOfScreen1() {
        yellowbird.setX(ground.getWidth() * 2);
    }

    @Test
    public void testBirdOutOfScreen2() {
        yellowbird.setX(-10);
    }
    @Test
    public void testBirdLaunch() throws BirdOutOfScreenException {
        int initialX = yellowbird.getX();
        int initialY = yellowbird.getY();

        yellowbird.launch(45, 10);
        yellowbird.move(1 / 60f);

        assertNotEquals(initialX, yellowbird.getX());
        assertNotEquals(initialY, yellowbird.getY());
    }
    @Test
    public void testBirdExhaustedState() throws BirdOutOfScreenException {
        assertFalse(yellowbird.isExhausted());

        yellowbird.move(1 / 60f);

        assertTrue(yellowbird.isExhausted());
    }
    @Test
    public void testBirdLaunchVelocity() {
        yellowbird.launch(45, 10);

        float expectedXVelocity = 10 * (float) Math.cos(Math.toRadians(45));
        float expectedYVelocity = 10 * (float) Math.sin(Math.toRadians(45));

        assertEquals(expectedXVelocity, yellowbird.getXVelocity(), 0.1);
        assertEquals(expectedYVelocity, yellowbird.getYVelocity(), 0.1);
    }
    @Test
    public void testBirdMove() throws BirdOutOfScreenException {
        int initialX = yellowbird.getX();
        int initialY = yellowbird.getY();

        yellowbird.setXVelocity(5);
        yellowbird.setYVelocity(5);

        yellowbird.move(1 / 60f);

        assertNotEquals(initialX, yellowbird.getX());
        assertNotEquals(initialY, yellowbird.getY());
    }


}
