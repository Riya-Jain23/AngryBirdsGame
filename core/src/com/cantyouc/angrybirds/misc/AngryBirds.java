package com.cantyouc.angrybirds.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cantyouc.angrybirds.Obstacle;
import com.cantyouc.angrybirds.Slingshot;
import com.cantyouc.angrybirds.menu.GameState;
import com.cantyouc.angrybirds.menu.MainMenu;
import com.cantyouc.angrybirds.bird.Bird;
import com.cantyouc.angrybirds.pig;

import java.util.List;


public class AngryBirds extends Game {
	public SpriteBatch batch;
	private Bird bird1, bird2;
	private Ground ground;
    private Music backgroundMusic;
    private MainScreen currentBirdIndex;
    private Slingshot slingshot;
    private Obstacle[] obstacles;
    private boolean birdLaunched;
    private Bird[] birds;
    private pig[] pigs;

    public AngryBirds() {
        this.currentBirdIndex = currentBirdIndex;
        this.slingshot = slingshot;
        this.obstacles = obstacles;
        this.birdLaunched = birdLaunched;
        this.birds = birds;
        this.pigs = pigs;
    }

    public void create() {
		batch = new SpriteBatch();
		ground = new Ground(Gdx.graphics.getWidth());
		bird1 = new Bird(100, 100, 59, 100, ground, false);
		bird2 = new Bird(1000, 2000, 59, 100, ground, true);
		this.setScreen(new MainMenu(this));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgmusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
	}



    public Bird getBird1() {
		return bird1;
	}

	public Bird getBird2() {
		return bird2;
	}

	public Ground getGround() {
		return ground;
	}

	public void render() {
		super.render();
	}

    public Bird[] getBirds() {
        return birds;
    }

    public pig[] getPigs() {
        return pigs;
    }

    public MainScreen getCurrentBirdIndex() {
        return currentBirdIndex;
    }

    public Slingshot getSlingshot() {
        return slingshot;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public boolean isBirdLaunched() {
        return birdLaunched;
    }

    public void setGame(GameState newGame) {
        this.setBirds(newGame.getBirds(ground));
        this.setpigs(newGame.getPigs());
        this.setCurrentBirdIndex(newGame.getCurrentBirdIndex());
        this.setSlingshot(newGame.getSlingshot());
        this.setObstacles(newGame.getObstacles());
    }

    private void setObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    private void setSlingshot(Slingshot slingshot) {
        this.slingshot = slingshot;
    }

    private void setCurrentBirdIndex(MainScreen currentBirdIndex) {
        this.currentBirdIndex = currentBirdIndex;
    }

    private void setpigs(pig[] pigs) {
        this.pigs = pigs;
    }

    private void setBirds(Bird[] birds) {
        this.birds = birds;
    }

}
