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
import com.cantyouc.angrybirds.misc.MainScreen;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveMenu extends Menu {
    private Button slot1, slot2, slot3, slot4;
    public SaveMenu(final AngryBirds game) {

        super(game);
        table.add(new Image(new Texture(Gdx.files.internal("saveGame.png")))).align(Align.center);
        table.row();

        addSlots();

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
        for (Button slot : slots) {
            if (slot != null) {  // Ensure that the slot is not null
                table.add(slot).growX().pad(10);
                table.row();
            } else {
                Gdx.app.log("SaveMenu", "Slot button is null. Slot may not have been initialized properly.");
            }
        }
    }

    private ImageButton makeOccupiedSlot() {
        return new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("slotOccupiedup.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("slotOccupieddown.png"))))
        );
    }

    private void addExistingSlot(int num) {
        if (num == 1)
            slot1 = makeOccupiedSlot();
        else if (num == 2)
            slot2 = makeOccupiedSlot();
        else if (num == 3)
            slot3 = makeOccupiedSlot();
        else
            slot4 = makeOccupiedSlot();
    }

    private void addEmptySlot(final int num) {
        final Button button = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("emptySlotUp.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("emptySlotDown.png"))))
        );

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Code reached here!");
                Gdx.app.log("SaveMenu", "Saving game to: " + Paths.get("game-" + num + ".dat"));
                // Save the game to that slot
                ObjectOutputStream out;
                try {
                    // Ensure the file exists by creating it if it doesn't
                    File file = new File("game-" + num + ".dat");
                    if (!file.exists()) {
                        file.createNewFile();  // This will create the file if it doesn't exist
                    }

                    out = new ObjectOutputStream(Files.newOutputStream(Paths.get("game-" + num + ".dat")));
                    out.writeObject(new GameState(game));  // Write the game state to the file
                    out.close();
                    Gdx.app.log("SaveMenu", "Game saved to slot " + num);
                } catch (IOException e) {
                    Gdx.app.log("AngryBirds", "Error saving game: " + e.getMessage());
                }

                // Change the button to occupied after saving the game
                if (num == 1) {
                    slot1 = makeOccupiedSlot();
                    button.remove();
                    table.add(slot1).growX().pad(10);
                } else if (num == 2) {
                    slot2 = makeOccupiedSlot();
                    button.remove();
                    table.add(slot2).growX().pad(10);
                } else if (num == 3) {
                    slot3 = makeOccupiedSlot();
                    button.remove();
                    table.add(slot3).growX().pad(10);
                } else {
                    slot4 = makeOccupiedSlot();
                    button.remove();
                    table.add(slot4).growX().pad(10);
                }

                game.setScreen(new PauseMenu(game));  // Go back to pause menu after saving
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
