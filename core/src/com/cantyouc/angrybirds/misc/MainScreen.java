package com.cantyouc.angrybirds.misc;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cantyouc.angrybirds.menu.VictoryMenu;
import com.cantyouc.angrybirds.pig;
import com.cantyouc.angrybirds.Obstacle;
import com.cantyouc.angrybirds.Slingshot;
import com.cantyouc.angrybirds.bird.Bird;
import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;
import com.cantyouc.angrybirds.menu.PauseMenu;

import java.io.Serializable;
import java.util.ArrayList;

public class MainScreen implements Screen, Serializable {
    private int width, height;
    private final Stage stage;
    private final Skin skin;
    private final Texture background;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final ShapeRenderer renderer;
    public final AngryBirds game;
    private Slingshot slingshot;
    private Bird[] birds;
    private int currentBirdIndex = 0;
    private Obstacle[] obstacles;
    private pig[] pigs;
    private Ground ground;
    private boolean isDragging = false;
    private float dragStartX, dragStartY, dragEndX, dragEndY;
    private boolean birdLaunched = false;
    private Bird currentBird;
    private boolean allTargetsCleared = false;
    private float launchPower = 1.0f;
    private static final float MAX_DRAG_DISTANCE = 100f;

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
//        Gdx.input.setInputProcessor(stage);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touch = new Vector2(screenX, screenY);
                viewport.unproject(touch);

                // Check if touch is near slingshot
                if (currentBird != null && !birdLaunched &&
                    Math.abs(touch.x - slingshot.getX()) < 50 &&
                    Math.abs(touch.y - slingshot.getY()) < 50) {
                    isDragging = true;
                    dragStartX = touch.x;
                    dragStartY = touch.y;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging && currentBird != null && !birdLaunched) {
                    Vector2 touch = new Vector2(screenX, screenY);
                    viewport.unproject(touch);

                    dragEndX = touch.x;
                    dragEndY = touch.y;

                    // Calculate drag distance and limit it
                    float dragDistance = Vector2.dst(dragStartX, dragStartY, dragEndX, dragEndY);
                    if (dragDistance > MAX_DRAG_DISTANCE) {
                        float angle = (float) Math.atan2(dragEndY - dragStartY, dragEndX - dragStartX);
                        dragEndX = dragStartX + MAX_DRAG_DISTANCE * (float) Math.cos(angle);
                        dragEndY = dragStartY + MAX_DRAG_DISTANCE * (float) Math.sin(angle);
                    }

                    // Update bird position while dragging
                    currentBird.setPosition(
                        slingshot.getX() + (dragEndX - dragStartX) - 15,
                        slingshot.getY() + (dragEndY - dragStartY) + 50
                    );
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging && currentBird != null && !birdLaunched) {
                    Vector2 touch = new Vector2(screenX, screenY);
                    viewport.unproject(touch);

                    // Calculate velocity based on drag
                    float vx = (dragStartX - dragEndX) * 0.25f; // Opposite to drag
                    float vy = (dragStartY - dragEndY) * 0.25f;

                    // Set bird's velocity
                    currentBird.setXVelocity(vx);
                    currentBird.setYVelocity(vy);

                    // Predict trajectory (e.g., maxTime = 3 seconds, deltaTime = 0.05s)
                    ArrayList<Object> trajectory = predictTrajectory(
                        currentBird.getX(), currentBird.getY(),
                        vx, vy,
                        0.05f, 3.0f
                    );

                    birdLaunched = true;
                    isDragging = false;
                    return true;
                }
                return false;
            }



        });
        Gdx.input.setInputProcessor(multiplexer);





        slingshot = new Slingshot(300, height / 2-400, 80, 150);
        ground = game.getGround();
        initializeBirds();
        initializeObstacles();
        initializePigs();
        createUI();

        setupNextBird();
//        birds = new Bird[3];
//        int birdWidth = 70;
//        int birdHeight = 70;
//        for (int i = 0; i < 3; i++) {
//            int birdXPosition = 100*i;
//            int birdYPosition = (int)(height / 2 + i * 50);
//            birds[i] = new Bird(birdXPosition, birdYPosition, birdHeight, birdWidth, ground, false);
//            birds[i].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird" + (i + 1) + ".png"))));
//        }
//
//        obstacles = new Obstacle[]{
//                new Obstacle(1500, height / 2 - 400, "obstacle1.png"),
//                new Obstacle(1500, height / 2 - 320, "obstacle1.png"),
//                new Obstacle(1500, height / 2 - 240, "obstacle1.png"),
//                new Obstacle(1650, height / 2 - 400, "obstacle1.png"),
//                new Obstacle(1650, height / 2 - 320, "obstacle1.png")
//        };
//        pigs = new pig[]{
//                new pig(1490, height / 2 - 160,0.3f),
//                new pig(1640, height / 2 - 240,0.3f)
//        };
//
//        Table root = new Table();
//        root.setFillParent(true);
//        stage.addActor(root);
//
//        Table table = new Table();
//        root.add(table).growX().align(Align.top).colspan(4);
//
//        Button pauseButton = new Button(skin);
//        Value buttonHeight = Value.percentHeight(2f, pauseButton);
//        Value buttonWidth = Value.percentWidth(2f, pauseButton);
//        Button.ButtonStyle style = new Button.ButtonStyle();
//        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("pauseUp.png"))));
//        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("pauseDown.png"))));
//        pauseButton.setStyle(style);
//
//        pauseButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new PauseMenu(game));
//                dispose();
//            }
//        }
//        );
//
//        table.add(pauseButton).align(Align.top | Align.left).padTop(10).expandX().width(buttonWidth).height(buttonHeight).padLeft(10);
//
//        Button invisButton = new Button(skin);
//        invisButton.setColor(0.5f, 0.5f, 0.5f, 0f);
//        table.add(invisButton).align(Align.top | Align.right).padTop(10).width(Value.percentWidth(1f, pauseButton)).expandX();
//
//        root.row();
//
//        // this touchpad will be converted to aim-angle-change touchpad
//        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
//        touchpadStyle.knob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("aim.png"))));
//        touchpadStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("transparent.png"))));
//        final Touchpad touchpad = new Touchpad(0, touchpadStyle);
//        touchpad.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if (touchpad.getKnobPercentX() > 0) {
//                    game.getBird2().setXVelocity(1);
//                } else if (touchpad.getKnobPercentX() < 0) {
//                    game.getBird2().setXVelocity(-1);
//                } else {
//                    game.getBird2().setXVelocity(0);
//                }
//            }
//        });
//
//        root.add(touchpad).expandX().colspan(4);
//        createButtons();
    }
    private void setupNextBird() {
        if (currentBirdIndex < birds.length) {
            currentBird = birds[currentBirdIndex];
            // Position bird at slingshot
            currentBird.setPosition(slingshot.getX()-15, slingshot.getY()+50);
            birdLaunched = false;
        }
    }
    private void initializeBirds() {
        birds = new Bird[3];
        int birdWidth = 70;
        int birdHeight = 80;
//        for (int i = 0; i < 3; i++) {
//            int birdXPosition = 150 * i;
//            int birdYPosition = (int)(height / 2 + i * 50);
//            birds[i] = new Bird(birdXPosition, birdYPosition, birdHeight, birdWidth, ground, false);
//            birds[i].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird" + (i + 1) + ".png"))));
//        }
        int birdXPosition1 = 150;
        int birdYPosition1 = 200;
        birds[0] = new Bird(birdXPosition1, birdYPosition1, birdHeight, birdWidth, ground, false);
        birds[0].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird1.png"))));

        int birdXPosition2 = 200;
        int birdYPosition2 = 150;
        birds[1] = new Bird(birdXPosition2, birdYPosition2, birdHeight, birdWidth, ground, false);
        birds[1].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird2.png"))));

        int birdXPosition3 = 100;
        int birdYPosition3 = 150;
        birds[2] = new Bird(birdXPosition3, birdYPosition3, birdHeight, birdWidth, ground, false);
        birds[2].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird3.png"))));
    }
    private void initializeObstacles() {
        obstacles = new Obstacle[]{
            new Obstacle(1500, height / 2 - 400, "obstacle1.png"),
            new Obstacle(1500, height / 2 - 320, "obstacle1.png"),
            new Obstacle(1500, height / 2 - 240, "obstacle1.png"),
            new Obstacle(1650, height / 2 - 400, "obstacle1.png"),
            new Obstacle(1650, height / 2 - 320, "obstacle1.png")
        };
    }
    private void createUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        createTopBar(root);
        createControlArea(root);
    }
    private void createTopBar(Table root) {
        Table table = new Table();
        root.add(table).growX().align(Align.top).colspan(4);

        Button pauseButton = createPauseButton();
        Value buttonHeight = Value.percentHeight(2f, pauseButton);
        Value buttonWidth = Value.percentWidth(2f, pauseButton);
        table.add(pauseButton).align(Align.top | Align.left).padTop(10).expandX().width(buttonWidth).height(buttonHeight).padLeft(10);

        Button invisButton = new Button(skin);
        invisButton.setColor(0.5f, 0.5f, 0.5f, 0f);
        table.add(invisButton).align(Align.top | Align.right).padTop(10).width(Value.percentWidth(1f, pauseButton)).expandX();
    }

    private Button createPauseButton() {
        Button pauseButton = new Button(skin);
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
        return pauseButton;
    }
    private void createControlArea(Table root) {
        root.row();

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.knob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("aim.png"))));
        touchpadStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("transparent.png"))));
        final Touchpad touchpad = new Touchpad(0, touchpadStyle);

        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (currentBird != null) {
                    if (touchpad.getKnobPercentX() > 0) {
                        currentBird.setXVelocity(1);
                    } else if (touchpad.getKnobPercentX() < 0) {
                        currentBird.setXVelocity(-1);
                    } else {
                        currentBird.setXVelocity(0);
                    }
                }
            }
        });

        root.add(touchpad).expandX().colspan(4);
    }
    private void initializePigs() {
        pigs = new pig[]{
            new pig(1490, height / 2 - 160, 0.3f),
            new pig(1640, height / 2 - 240, 0.3f)
        };
    }

//    private void createButtons() {
//        Button victoryButton = new Button(skin);
//        victoryButton.add(new Label("Show Victory", skin));
//        victoryButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new VictoryMenu(game)); // Show victory menu
//            }
//        });
//
//        Button failButton = new Button(skin);
//        failButton.add(new Label("Show Fail", skin));
//        failButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new FailMenu(game)); // Show fail menu
//            }
//        });
//
//        Table buttonTable = new Table();
//        buttonTable.setFillParent(true);
//        buttonTable.top().right();
//        buttonTable.add(victoryButton).pad(10).width(200).height(50).align(Align.bottomLeft);
//        buttonTable.row();
//        buttonTable.add(failButton).pad(10).width(200).height(50).align(Align.bottomLeft);
//
//        stage.addActor(buttonTable);
//    }
private boolean checkPigCollision(Bird bird, pig pig) {
    if (pig == null) return false;

    // Simple axis-aligned bounding box (AABB) collision detection
    return bird.getX() < pig.getX() + pig.getWidth() &&
        bird.getX() + bird.getWidth() > pig.getX() &&
        bird.getY() < pig.getY() + pig.getHeight() &&
        bird.getY() + bird.getHeight() > pig.getY();
}

    private void destroyPig(int pigIndex) {
        try {
            if (pigIndex >= 0 && pigIndex < pigs.length && pigs[pigIndex] != null) {
                // Dispose of the pig's resources
                pigs[pigIndex].dispose();

                // Remove the pig from the array
                pigs[pigIndex] = null;

                // Check if all pigs are destroyed
                checkLevelCompletion();
            }
        } catch (Exception e) {
            // Log the error or handle it appropriately
            System.err.println("Error destroying pig: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkLevelCompletion() {
        boolean allPigsDestroyed = true;
        for (pig pig : pigs) {
            if (pig != null) {
                allPigsDestroyed = false;
                break;
            }
        }

        if (allPigsDestroyed) {
            // Transition to victory screen
            game.setScreen(new VictoryMenu(game));
            dispose();
        }
    }
    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        viewport.update(width, height);

        game.batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, width, height);

        slingshot.draw(game.batch);

        // Move and draw birds
        for (Bird bird : birds) {
            try {
                bird.draw(game.batch);
                if (bird == currentBird && birdLaunched) {
                    bird.move(deltaTime);

                    // Check for collisions with obstacles
                    for (Obstacle obstacle : obstacles) {
                        if (obstacle.isHitByBird(bird)) {
                            // Apply a velocity to the obstacle when it is hit by the bird
                            float pushX = (bird.getXVelocity() * 15f); // Adjust force multiplier for more impact
                            float pushY = (bird.getYVelocity() * 15f); // Adjust force multiplier for more impact
                            obstacle.push(pushX, pushY);
                            obstacle.crumble();
                        }
                    }
                    for (int i = 0; i < pigs.length; i++) {
                        if (checkPigCollision(bird, pigs[i])) {
                            destroyPig(i);
                        }
                    }
                    if (Math.abs(bird.getXVelocity()) < 0.1 && Math.abs(bird.getYVelocity()) < 0.1) {
                        currentBirdIndex++;
                        setupNextBird();
                    }
                }
            } catch (BirdOutOfScreenException ex) {
                bird.setXVelocity(0);
                bird.setYVelocity(0);
                currentBirdIndex++;
                setupNextBird();
            }
        }

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.update(deltaTime); // Update obstacle's position
            obstacle.draw(game.batch);
        }

        for (pig pig : pigs) {
            if (pig != null) {
                pig.draw(game.batch);
            }
        }

        game.batch.end();

        renderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0f, 1f, 0f, 1f);
        ground.draw(renderer);
        renderer.end();

        // Draw trajectory prediction (only while dragging and before bird launch)
        if (isDragging && currentBird != null && !birdLaunched) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(1, 0, 0, 1); // Red color for trajectory

            // Predict trajectory based on current drag
            ArrayList<Object> trajectory = predictTrajectory(
                currentBird.getX(), currentBird.getY(),
                (dragStartX - dragEndX) * 0.25f,
                (dragStartY - dragEndY) * 0.25f,
                0.05f, 3.0f
            );

            renderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        stage.act();
        stage.draw();
    }

    // Collision detection method
    private boolean checkCollision(Bird bird, Obstacle obstacle) {
        // Simple axis-aligned bounding box (AABB) collision detection
        return bird.getX() < obstacle.getX() + obstacle.getWidth() &&
            bird.getX() + bird.getWidth() > obstacle.getX() &&
            bird.getY() < obstacle.getY() + obstacle.getHeight() &&
            bird.getY() + bird.getHeight() > obstacle.getY();
    }




    public ArrayList<Object> predictTrajectory(float initialX, float initialY, float initialXVelocity, float initialYVelocity, float deltaTime, float maxTime) {
        ArrayList<Object> trajectoryPoints = new ArrayList<>();

        // Initialize the position and velocity
        float x = initialX;
        float y = initialY;
        float xVelocity = initialXVelocity;
        float yVelocity = initialYVelocity;

        // Simulate the trajectory over time
        for (float t = 0; t <= maxTime; t += deltaTime) {
            // Add the current position to the trajectory points
            trajectoryPoints.add(new Vector2(x, y));

            // Update the position based on velocity and deltaTime
            x += xVelocity * deltaTime;
            y += yVelocity * deltaTime;

            // Update the vertical velocity based on gravity
            yVelocity -= Ground.GRAVITY * deltaTime;

            // If the bird hits the ground (y <= ground height), stop the prediction
            if (y <= ground.getHeight()) {
                y = ground.getHeight();
                break;
            }
        }

        return trajectoryPoints;
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
        for (pig pig : pigs) {
            pig.dispose();
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
