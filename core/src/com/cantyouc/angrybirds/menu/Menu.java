package com.cantyouc.angrybirds.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cantyouc.angrybirds.misc.AngryBirds;
import com.cantyouc.angrybirds.misc.AngryBirds;

public class Menu implements Screen {
    protected final AngryBirds game;
    protected final Stage stage;
    protected Texture background;
    protected Skin skin;
    protected Table table;

    public Menu(final AngryBirds game) {
        skin = new Skin(Gdx.files.internal("orangepeelui/uiskin.json"));
        this.game = game;
        background = new Texture("background.jpg");
        stage = new Stage(new ScreenViewport());
        table = new Table();
        Gdx.input.setInputProcessor(stage);
    }

    public void setUi(Table ui) {
        ui.setFillParent(true);
        stage.addActor(ui);
    }

    @Override
    public void render(float d) {
        game.batch.begin();
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.act(d);
        stage.draw();
    }

    @Override
    public void show() {}

    @Override
    public void resize (int width,int height) {}

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
