package com.cantyouc.angrybirds.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cantyouc.angrybirds.menu.MainMenu;
import com.cantyouc.angrybirds.bird.Bird;


public class AngryBirds extends Game {
	public SpriteBatch batch;
	private Bird bird1, bird2;
	private Ground ground;
    private Music backgroundMusic;

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
}
