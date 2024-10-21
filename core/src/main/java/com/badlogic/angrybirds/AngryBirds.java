package com.badlogic.angrybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
public class AngryBirds extends ApplicationAdapter {
    private Stage stage;
    private Button menuButton;
    private Skin skin;
    private Table table;
    @Override
    public void create () {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("orangepeelui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        menuButton = new Button(skin);
        table.add(menuButton).align(Align.top | Align.left).pad(10).expand();
        Button healthBar1 = new Button(skin);
        table.add(healthBar1).align(Align.top|Align.right).pad(10).expand().width(Value.percentWidth(8f, menuButton));

        Button vs = new Button(skin);
        table.add(vs).align(Align.top).pad(10).expandY().width(Value.percentWidth(1f, menuButton));
        Button healthBar2 = new Button(skin);
        table.add(healthBar2).align(Align.top | Align.left).pad(10).expand().width(Value.percentWidth(8f, menuButton));
        table.add().expand().pad(35);
    }
    @Override
    public void render () {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        skin.dispose();
        stage.dispose();
    }
}
