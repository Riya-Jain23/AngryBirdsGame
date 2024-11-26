package com.cantyouc.angrybirds.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cantyouc.angrybirds.misc.AngryBirds;
import com.cantyouc.angrybirds.misc.MainScreen;

public class FailMenu implements Screen {
    private final AngryBirds game;
    private final Stage stage;
    private final Skin skin;
    private final Texture background;
    private final Texture backButtonImage;
    private final Texture restartButtonImage;

    public FailMenu(AngryBirds game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.skin = new Skin(Gdx.files.internal("orangepeelui/uiskin.json"));
        this.background = new Texture("fail_background.png"); // Your fail background image
        this.backButtonImage = new Texture("back_to_main_menu.png"); // Image for "Back to Main Menu" button
        this.restartButtonImage = new Texture("restart_level.png"); // Image for "Restart Level" button

        float buttonWidth = 200f;
        float buttonHeight = 200f;

        Button backButton = new Button(new TextureRegionDrawable(backButtonImage));
        Button restartButton = new Button(new TextureRegionDrawable(restartButtonImage));

        backButton.setSize(buttonWidth, buttonHeight);
        restartButton.setSize(buttonWidth, buttonHeight);

        backButton.addListener(event -> {
            if (event.isHandled()) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
            return false;
        });

        restartButton.addListener(event -> {
            if (event.isHandled()) {
                game.setScreen(new MainScreen(game, 1));
                dispose();
            }
            return false;
        });

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.bottom().padBottom(50);

        buttonTable.add(backButton).size(buttonWidth, buttonHeight).padRight(20);
        buttonTable.add(restartButton).size(buttonWidth, buttonHeight);

        stage.addActor(buttonTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        skin.dispose();
        backButtonImage.dispose();
        restartButtonImage.dispose();
    }
}

