package com.cantyouc.angrybirds.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.cantyouc.angrybirds.misc.AngryBirds;
import com.cantyouc.angrybirds.misc.MainScreen;
import com.cantyouc.angrybirds.misc.AngryBirds;

public class PauseMenu extends Menu {
    public PauseMenu(final AngryBirds game) {
        super(game);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        ImageButton resume = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resumeUp.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("resumeDown.png"))))
        );
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game));
                dispose();
            }
        });

        ImageButton save = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("saveUp.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("saveDown.png"))))
        );

        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SaveMenu(game));
                dispose();
            }
        });

        ImageButton returnHome = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("returnHomeUp.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("returnHomeDown.png"))))
        );

        returnHome.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        table.row();
        table.add(resume).pad(20);
        table.row();
        table.add(save).pad(20);
        table.row();
        table.add(returnHome).pad(20);
        setUi(table);
    }
}
