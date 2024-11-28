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
import com.cantyouc.angrybirds.misc.AngryBirds;
import com.cantyouc.angrybirds.misc.MainScreen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadMenu extends Menu {
    private Button slot1, slot2, slot3, slot4;
    private int level;

    public LoadMenu(final AngryBirds game) {
        super(game);

        background = new Texture(Gdx.files.internal("background.jpg"));
        table.add(new Image(new Texture(Gdx.files.internal("loadGame.png")))).align(Align.center);
        table.row();

        addSlots();

        Button goBack = new Button(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("goBackUp.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("goBackDown.png"))))
        );

        goBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        table.add(goBack).pad(50);
        setUi(table);
    }

    private void addSlots() {
        for (int i=0; i < 4; i++) {
            File f = new File("game-" + (i+1) + ".dat");
            if (f.exists()) {
                addExistingSlot(i + 1);
            } else {
                addEmptySlot(i + 1);
            }
        }

        Button[] slots = {slot1, slot2, slot3, slot4};
        for (Button slot: slots) {
            table.add(slot).growX().pad(10);
            table.row();
        }
    }

    private ImageButton makeEmptySlot() {
        return new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("emptySlotUp.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("emptySlotDown.png"))))
        );
    }

    private void addEmptySlot(int num) {
        if (num == 1)
            slot1 = makeEmptySlot();
        else if (num == 2)
            slot2 = makeEmptySlot();
        else if (num == 3)
            slot3 = makeEmptySlot();
        else
            slot4 = makeEmptySlot();
    }

    private void addExistingSlot(final int num) {
        final Button button = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("loadSlot"+num+".png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("loadSlotd"+num+".png"))))
        );

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ObjectInputStream in;
                GameState loadedGame;
                try {
                    in = new ObjectInputStream(Files.newInputStream(Paths.get("game-" + num + ".dat")));
                    loadedGame = (GameState) in.readObject();
                    in.close();
                } catch (IOException | ClassNotFoundException e) {
                    Gdx.app.log("AngryBirds", e.getMessage());
                    return;
                }
                game.setGame(loadedGame);
                game.setScreen(new MainScreen(game, 1));
                dispose();
            }
        });

        if (num == 1)
            slot1 = button;
        else if (num == 2)
            slot2 = button;
        else if (num == 3)
            slot3 = button;
        else
            slot4 = button;
    }
}
