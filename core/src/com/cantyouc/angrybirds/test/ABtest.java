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

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ABtest {
    private static final int TIMEOUT = 5000;
    private static Ground ground;
    private static YellowBird bird;

    @BeforeClass
    public static void setUp() {
        ground = new Ground(1000);     // a sample Ground
        bird = new YellowBird(200, 200, 20, 20, ground, false);     // a sample Tank
    }

    @Test(expected = BirdOutOfScreenException.class)
    public void testBirdOutOfScreen1() throws BirdOutOfScreenException {
        bird.setX(ground.getWidth() * 2);
    }

    @Test(expected = BirdOutOfScreenException.class)
    public void testBirdOutOfScreen2() throws BirdOutOfScreenException {
        bird.setX(-10);
    }

}
