package com.cantyouc.angrybirds.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.cantyouc.angrybirds.misc.AngryBirds;

public class SaveMenu extends Menu {
    public SaveMenu(final AngryBirds game) {

        super(game);
        Table table = new Table();
        table.add(new Image(new Texture(Gdx.files.internal("saveGame.png")))).align(Align.center);
        table.row();

        // Hard Coding 4 buttons for now
        // This will need to be changed into a manual list of 4 buttons with the dates & times of save // "name" of saved game
        for (int i = 0; i < 4; i++) {
            table.add(new ImageButton(
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("emptySlotUp.png")))),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("emptySlotDown.png"))))
            )).growX().pad(10);
            table.row();
        }

        Button goBack = new Button(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("goBackUp.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("goBackDown.png"))))
        );

        goBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PauseMenu(game));
                dispose();
            }
        });

        table.add(goBack).pad(50);
        setUi(table);
    }
}
