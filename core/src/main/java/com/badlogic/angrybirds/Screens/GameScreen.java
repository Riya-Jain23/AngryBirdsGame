package com.badlogic.angrybirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.angrybirds.Button;
import com.badlogic.angrybirds.AssetLoader;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Texture background;
    private Button playButton;
    private Button pauseButton;

    @Override
    public void show() {
        batch = new SpriteBatch();
        AssetLoader.load(); // Load all assets
        background = AssetLoader.background;

        // Create buttons with their positions
        playButton = new Button("play.png", 150, 300, 100, 100);
        pauseButton = new Button("pause.png", 300, 300, 100, 100);

        // Handle user input
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (playButton.isClicked(screenX, Gdx.graphics.getHeight() - screenY)) {
                    System.out.println("Play button clicked!");
                }
                if (pauseButton.isClicked(screenX, Gdx.graphics.getHeight() - screenY)) {
                    System.out.println("Pause button clicked!");
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Full-screen background
        playButton.render(batch);
        pauseButton.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        playButton.dispose();
        pauseButton.dispose();
    }
}

