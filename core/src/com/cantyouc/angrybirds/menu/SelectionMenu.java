package com.cantyouc.angrybirds.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.cantyouc.angrybirds.misc.MainScreen;
import com.cantyouc.angrybirds.misc.AngryBirds;

public class SelectionMenu extends Menu {
    private Texture chooseLevelTexture;

    public SelectionMenu(final AngryBirds game) {
        super(game);

        chooseLevelTexture = new Texture(Gdx.files.internal("chooselevel.png"));
        Image chooseLevelImage = new Image(chooseLevelTexture);

        Button level1Button = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level1.png")))));
        Button level2Button = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level2.png")))));
        Button level3Button = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level3.png")))));

        level1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(new MainScreen(game)); // replace
            }
        });

        level2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game)); // replace
            }
        });

        level3Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Handle level 3 selection
                game.setScreen(new MainScreen(game)); // replace
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(chooseLevelImage).align(Align.left).pad(300).expandY().width(500).height(200);

        Table buttonTable = new Table();
        buttonTable.add(level1Button).pad(10).width(200).height(200);
        buttonTable.row();
        buttonTable.add(level2Button).pad(10).width(200).height(200);
        buttonTable.row();
        buttonTable.add(level3Button).pad(10).width(200).height(200);
        table.add(buttonTable).expandY().align(Align.right).pad(20);
    }

    @Override
    public void render(float d) {
        game.batch.begin();
        game.batch.draw(new Texture(Gdx.files.internal("background.jpg")), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.act(d);
        stage.draw();
    }

    @Override
    public void dispose() {
        chooseLevelTexture.dispose();

    }
}
