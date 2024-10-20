package com.badlogic.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader implements Disposable {
    public static Texture background;
    public static Texture playButton;
    public static Texture playButtonHover;
    public static Texture pauseButton;
    public static Texture pauseButtonHover;
    public static Texture bird;
    public static Texture pig;

    public static void load() {
        background = new Texture("background.png");
        playButton = new Texture("play.png");
        playButtonHover = new Texture("play_hover.png");
        pauseButton = new Texture("pause.png");
        pauseButtonHover = new Texture("pause_hover.png");
        bird = new Texture("bird.png");
        pig = new Texture("pig.png");
    }

    public void dispose() {
        background.dispose();
        playButton.dispose();
        playButtonHover.dispose();
        pauseButton.dispose();
        pauseButtonHover.dispose();
        bird.dispose();
        pig.dispose();
    }
}
