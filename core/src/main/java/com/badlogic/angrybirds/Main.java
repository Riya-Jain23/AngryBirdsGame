package com.badlogic.angrybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;

    @Override
    public void create() {
        // Set up the stage and load skin for GUI elements
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load the default skin (or use a custom one)
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Make sure this file exists

        // Create a table to hold GUI elements
        Table table = new Table();
        table.setFillParent(true); // Ensure the table fills the entire screen
        stage.addActor(table);

        // Add a label
        Label titleLabel = new Label("Angry Birds Game", skin);
        table.add(titleLabel).padBottom(40).center();
        table.row();

        // Add a button to start the game
        TextButton startButton = new TextButton("Start Game", skin);
        table.add(startButton).padBottom(20).center();
        table.row();

        // Add a button to show game settings
        TextButton settingsButton = new TextButton("Settings", skin);
        table.add(settingsButton).padBottom(20).center();
        table.row();

        // Add a button to exit the game
        TextButton exitButton = new TextButton("Exit", skin);
        table.add(exitButton).padBottom(20).center();
    }

    @Override
    public void render() {
        // Clear the screen and render the stage (GUI)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
