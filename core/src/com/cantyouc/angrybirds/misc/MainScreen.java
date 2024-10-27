package com.cantyouc.angrybirds.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cantyouc.angrybirds.Alien;
import com.cantyouc.angrybirds.Obstacle;
import com.cantyouc.angrybirds.Slingshot;
import com.cantyouc.angrybirds.bird.Bird;
import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;
import com.cantyouc.angrybirds.menu.FailMenu;
import com.cantyouc.angrybirds.menu.PauseMenu;
import com.cantyouc.angrybirds.menu.VictoryMenu;

public class MainScreen implements Screen {
    private int width, height;
    private final Stage stage;
    private final Skin skin;
    private final Texture background;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final ShapeRenderer renderer;
    public final AngryBirds game;
    public int numAirDrops;
    private Slingshot slingshot; // Add slingshot
    private Bird[] birds; // Array for three birds
    private Obstacle[] obstacles; // Array for obstacles
    private Alien[] aliens; // Array for aliens
    private Ground ground;
    private boolean playerHasWon;
    private boolean playerHasLost;

    public MainScreen(final AngryBirds game) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        this.game = game;
        renderer = new ShapeRenderer();
        background = new Texture("background.jpg");

        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false, width, height);
        camera.update();

        viewport = new FitViewport(width, height, camera);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("orangepeelui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        slingshot = new Slingshot(300, height / 2-400, 80, 160);
//        ground = new Ground(width); // Create ground with screen width

        ground = game.getGround();
        birds = new Bird[3];
        int birdWidth = 100; // Example width for the bird
        int birdHeight = 100; // Example height for the bird
        for (int i = 0; i < 3; i++) {
            int birdXPosition = 100*i; // Set a new x position
            int birdYPosition = (int)(height / 2 + i * 50); // Set a new y position
            birds[i] = new Bird(birdXPosition, birdYPosition, birdHeight, birdWidth, ground, false);
            birds[i].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird" + (i + 1) + ".png"))));
        }

        obstacles = new Obstacle[]{
                new Obstacle(1500, height / 2-400),
                new Obstacle(1500, height / 2 -320)
        };
        aliens = new Alien[]{
                new Alien(1450, height / 2 - 240,0.5f)
        };

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Table table = new Table();
        root.add(table).growX().align(Align.top).colspan(4);

        Button pauseButton = new Button(skin);
        Value buttonHeight = Value.percentHeight(2f, pauseButton);
        Value buttonWidth = Value.percentWidth(2f, pauseButton);
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("pauseUp.png"))));
        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("pauseDown.png"))));
        pauseButton.setStyle(style);

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PauseMenu(game));
                dispose();
            }
        });

        table.add(pauseButton).align(Align.top | Align.left).padTop(10).expandX().width(buttonWidth).height(buttonHeight).padLeft(10);

        Button invisButton = new Button(skin);
        invisButton.setColor(0.5f, 0.5f, 0.5f, 0f);
        table.add(invisButton).align(Align.top | Align.right).padTop(10).width(Value.percentWidth(1f, pauseButton)).expandX();

        root.row();

        // this touchpad will be converted to aim-angle-change touchpad
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.knob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("aim.png"))));
        touchpadStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("transparent.png"))));
        final Touchpad touchpad = new Touchpad(0, touchpadStyle);
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (touchpad.getKnobPercentX() > 0) {
                    game.getBird2().setXVelocity(1);
                } else if (touchpad.getKnobPercentX() < 0) {
                    game.getBird2().setXVelocity(-1);
                } else {
                    game.getBird2().setXVelocity(0);
                }
            }
        });

        root.add(touchpad).expandX().colspan(4);
        createButtons();
    }

    private void createButtons() {
        Button victoryButton = new Button(skin);
        victoryButton.add(new Label("Show Victory", skin));
        victoryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new VictoryMenu(game)); // Show victory menu
            }
        });

        Button failButton = new Button(skin);
        failButton.add(new Label("Show Fail", skin));
        failButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FailMenu(game)); // Show fail menu
            }
        });

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.top().right(); // Aligns the table to the top-right corner
        buttonTable.add(victoryButton).pad(10).width(200).height(50).align(Align.bottomLeft);
        buttonTable.row();
        buttonTable.add(failButton).pad(10).width(200).height(50).align(Align.bottomLeft);

        stage.addActor(buttonTable);
    }

    @Override
    public void render(float d) {
        //Log screen width and height
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        viewport.update(width, height);
        game.batch.setProjectionMatrix(stage.getViewport().getCamera().combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, width, height);

//        try {
//            game.getBird1().draw(game.batch);
//            game.getBird1().move();
//        }
//        catch (BirdOutOfScreenException ex) {
//            game.getBird1().setXVelocity(0);
//        }
//
//        try {
//            game.getBird2().draw(game.batch);
//            game.getBird2().move();
//        }
//        catch (BirdOutOfScreenException ex) {
//            game.getBird2().setXVelocity(0);
//        }
        slingshot.draw(game.batch);

        // Draw birds
        for (int i = 0; i < birds.length; i++) {
            try {
                birds[i].draw(game.batch);
                birds[i].move();
            } catch (BirdOutOfScreenException ex) {
                birds[i].setXVelocity(0);
            }
        }

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(game.batch);
        }

        // Draw aliens
        for (Alien alien : aliens) {
            alien.draw(game.batch);
        }
        game.batch.end();

        renderer.setColor(0f, 1f, 0f, 1f);
        renderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        game.getGround().draw(renderer);
        renderer.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        stage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }
        for (Alien alien : aliens) {
            alien.dispose();
        }
        for (Bird bird : birds) {
            if (bird.getImage() != null && bird.getImage().getTexture() != null) {
                bird.getImage().getTexture().dispose(); // Dispose the texture
            }
        }
        skin.dispose();
        stage.dispose();
        background.dispose();
    }
}
