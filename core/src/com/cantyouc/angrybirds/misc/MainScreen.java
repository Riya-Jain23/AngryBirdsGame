package com.cantyouc.angrybirds.misc;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.cantyouc.angrybirds.*;
import com.cantyouc.angrybirds.bird.Black;
import com.cantyouc.angrybirds.bird.YellowBird;
import com.cantyouc.angrybirds.menu.FailMenu;
import com.cantyouc.angrybirds.menu.VictoryMenu;
import com.cantyouc.angrybirds.bird.Bird;
import com.cantyouc.angrybirds.exception.BirdOutOfScreenException;
import com.cantyouc.angrybirds.menu.PauseMenu;

import java.io.Serializable;
import java.util.ArrayList;

public class MainScreen extends ScreenAdapter implements Screen, Serializable  {
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
    private final int level;
    private int currentBirdIndex = 0;
    private BaseObstacle[] obstacles;
    private pig[] pigs;
    private Ground ground;
    private boolean isDragging = false;
    private float dragStartX, dragStartY, dragEndX, dragEndY;
    private boolean birdLaunched = false;
    private Bird currentBird;
    private boolean allTargetsCleared = false;
    private boolean allBirdsExhausted = true;
    private float launchPower = 1.0f;
    private static final float MAX_DRAG_DISTANCE = 100f;
    private float levelCompletionDelay = 0f;
    private boolean levelCompleted = false;
    private boolean spacebarPressed;


    public MainScreen(final AngryBirds game, int level) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        this.game = game;
        this.level = level;
        renderer = new ShapeRenderer();
        background = new Texture("background.jpg");

        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false, width, height);
        camera.update();

        viewport = new FitViewport(width, height, camera);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("orangepeelui/uiskin.json"));
        slingshot = new Slingshot(300, height / 2-400, 80, 150);
        ground = game.getGround();
        switch (level) {
            case 1:
                initializeBirds();
                initializeObstacles();
                initializePigs();
                break;
            case 2:
                initializeBirdsLevel2();
                initializeObstaclesLevel2();
                initializePigsLevel2();
                break;
            case 3:
                initializeBirdsLevel3();
                initializeObstaclesLevel3();
                initializePigsLevel3();
                break;
            default:
                initializeBirds();
                initializeObstacles();
                initializePigs();
        }
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touch = new Vector2(screenX, screenY);
                viewport.unproject(touch);

                if (currentBird != null && !birdLaunched) {
                    float birdX = currentBird.getX();
                    float birdY = currentBird.getY();
                    float birdWidth = currentBird.getWidth();
                    float birdHeight = currentBird.getHeight();

                    if (touch.x >= birdX && touch.x <= birdX + birdWidth &&
                        touch.y >= birdY && touch.y <= birdY + birdHeight) {
                        isDragging = true;
                        dragStartX = touch.x;
                        dragStartY = touch.y;
                        return true;
                    }
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

                    float dragDistance = Vector2.dst(dragStartX, dragStartY, dragEndX, dragEndY);

                    if (dragDistance > MAX_DRAG_DISTANCE) {
                        float angle = (float) Math.atan2(dragEndY - dragStartY, dragEndX - dragStartX);
                        dragEndX = dragStartX + MAX_DRAG_DISTANCE * (float) Math.cos(angle);
                        dragEndY = dragStartY + MAX_DRAG_DISTANCE * (float) Math.sin(angle);
                    }

                    float xVelocity = (dragStartX - dragEndX) * 0.25f;
                    float yVelocity = (dragStartY - dragEndY) * 0.25f;

                    currentBird.setPosition(
                        slingshot.getX() + (dragEndX - dragStartX) - 15,
                        slingshot.getY() + (dragEndY - dragStartY) + 50
                    );

                    currentBird.setXVelocity(xVelocity);
                    currentBird.setYVelocity(yVelocity);

                    if (currentBird != null) {
                        drawTrajectory(game.batch, currentBird.getX(), currentBird.getY(), xVelocity, yVelocity, currentBird.getImage());
                    }

                    return true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging && currentBird != null && !birdLaunched) {
                    Vector2 touch = new Vector2(screenX, screenY);
                    viewport.unproject(touch);

                    float vx = (dragStartX - dragEndX) * 0.25f;
                    float vy = (dragStartY - dragEndY) * 0.25f;

                    currentBird.setXVelocity(vx);
                    currentBird.setYVelocity(vy);

                    birdLaunched = true;
                    isDragging = false;


                    if (birdLaunched) {

                        drawTrajectory(game.batch, currentBird.getX(), currentBird.getY(), vx, vy, currentBird.getImage());
                    }

                    return true;
                }
                return false;
            }


            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    if (currentBird instanceof Black && birdLaunched) {
                        Black blackBird = (Black) currentBird;
                        if (!blackBird.hasExploded()) {
                            blackBird.setSpacebarPressed(true);
                            blackBird.triggerExplosion();
                            return true;
                        }
                    }
                }
                return false;
            }



        });
        Gdx.input.setInputProcessor(multiplexer);

        createUI();
        setupNextBird();
    }
    private void setupNextBird() {
        if (currentBirdIndex < birds.length) {
            currentBird = birds[currentBirdIndex];
            if (currentBird != null && !currentBird.isExhausted()) {

                currentBird.setPosition(slingshot.getX() - 15, slingshot.getY() + 50);
                birdLaunched = false;
            }
            else{
                currentBird.isExhausted();
                currentBirdIndex++;
                setupNextBird();
            }
        }

    }
    private void initializeBirds() {
        birds = new Bird[3];
        int birdWidth = 70;
        int birdHeight = 80;

        int birdXPosition1 = 150;
        int birdYPosition1 = 200;
        birds[0] = new Bird(birdXPosition1, birdYPosition1, birdHeight, birdWidth, ground, false);
        birds[0].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird1.png"))));

        int birdXPosition2 = 200;
        int birdYPosition2 = 150;
        birds[1] = new Black(birdXPosition2, birdYPosition2, birdHeight, birdWidth, ground, false);
        birds[1].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird2.png"))));

        int birdXPosition3 = 100;
        int birdYPosition3 = 150;
        birds[2] = new YellowBird(birdXPosition3, birdYPosition3, birdHeight, birdWidth, ground, false);
        birds[2].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird3.png"))));
    }
    private void initializeObstacles() {
        obstacles = new BaseObstacle[]{
            new WoodenObstacle(1500, height / 2 - 400),
            new WoodenObstacle(1500, height / 2 - 320),
            new WoodenObstacle(1500, height / 2 - 240),
            new WoodenObstacle(1650, height / 2 - 400),
            new WoodenObstacle(1650, height / 2 - 320)
        };
    }
    private void initializeBirdsLevel2() {
        birds = new Bird[3];
        int birdWidth = 70;
        int birdHeight = 80;

        int birdXPosition1 = 150;
        int birdYPosition1 = 200;
        birds[0] = new Bird(birdXPosition1, birdYPosition1, birdHeight, birdWidth, ground, false);
        birds[0].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird1.png"))));

        int birdXPosition2 = 200;
        int birdYPosition2 = 150;
        birds[1] = new Black(birdXPosition2, birdYPosition2, birdHeight, birdWidth, ground, false);
        birds[1].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird2.png"))));

        int birdXPosition3 = 100;
        int birdYPosition3 = 150;
        birds[2] = new YellowBird(birdXPosition3, birdYPosition3, birdHeight, birdWidth, ground, false);
        birds[2].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird3.png"))));
    }

    private void initializeObstaclesLevel2() {
        obstacles = new BaseObstacle[]{
            new WoodenObstacle(1300, height / 2 - 400),
            new GlassObstacle(1500, height / 2 - 400),
            new WoodenObstacle(1300, height / 2 - 320),
            new WoodenObstacle(1500, height / 2 - 320),
            new GlassObstacle(1500, height / 2 - 240),
            new WoodenObstacle(1300, height / 2 - 240),
            new WoodenObstacle(1300, height / 2 - 160),
//            new WoodenObstacle(1300, height / 2 - 160),
            new GlassObstacle(1100, height / 2 - 160),
//            new WoodenObstacle(1250, height / 2 - 60),
//
            new MetalObstacle(1100, height / 2 - 400),
            new GlassObstacle(1100, height / 2 - 320),
            new MetalObstacle(1100, height / 2 - 240),
            new GlassObstacle(1100, height / 2 - 160),
            new MetalObstacle(1100, height / 2 - 80),
            new GlassObstacle(1100, height / 2)
        };
    }

    private void initializePigsLevel2() {
        pigs = new pig[]{
            new pig(1480, height / 2 - 160, 0.3f, ground, "pig2.png"),
            new pig(1280, height / 2 - 80, 0.3f, ground, "pig2.png"),
            new pig(1080, height / 2 + 80, 0.3f, ground, "pig2.png")
        };
    }

    private void initializeBirdsLevel3() {
        birds = new Bird[4];
        int birdWidth = 70;
        int birdHeight = 80;

        int birdXPosition1 = 150;
        int birdYPosition1 = 200;
        birds[0] = new Bird(birdXPosition1, birdYPosition1, birdHeight, birdWidth, ground, false);
        birds[0].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird1.png"))));

        int birdXPosition2 = 200;
        int birdYPosition2 = 150;
        birds[1] = new Black(birdXPosition2, birdYPosition2, birdHeight, birdWidth, ground, false);
        birds[1].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird2.png"))));

        int birdXPosition3 = 100;
        int birdYPosition3 = 150;
        birds[2] = new YellowBird(birdXPosition3, birdYPosition3, birdHeight, birdWidth, ground, false);
        birds[2].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird3.png"))));

        int birdXPosition4 = 20;
        int birdYPosition4 = 150;
        birds[3] = new YellowBird(birdXPosition4, birdYPosition4, birdHeight, birdWidth, ground, false);
        birds[3].setImage(new TextureRegion(new Texture(Gdx.files.internal("bird3.png"))));
    }

    private void initializeObstaclesLevel3() {
        obstacles = new BaseObstacle[]{
            new MetalObstacle(1500, height / 2 - 400),
            new MetalObstacle(1500, height / 2 - 320),
            new WoodenObstacle(1500, height / 2 - 240),

            new GlassObstacle(1300, height / 2 - 240),
            new MetalObstacle(1300, height / 2 - 160),
            new GlassObstacle(1300, height / 2 - 320),
            new GlassObstacle(1300, height / 2 - 400),

            new MetalObstacle(1100, height / 2 - 400),
            new MetalObstacle(1100, height / 2 - 320),
            new MetalObstacle(1100, height / 2 - 240),
            new MetalObstacle(1100, height / 2 - 160),
            new GlassObstacle(1100, height / 2 - 80),
            new MetalObstacle(1100, height / 2),
        };
    }

    private void initializePigsLevel3() {
        pigs = new pig[]{
            new pig(1600, height / 2 - 400, 0.3f, ground, "pig3.png"),
            new pig(1485, height / 2 - 160, 0.3f, ground, "pig3.png"),
            new pig(1285, height / 2 - 80, 0.3f, ground, "pig3.png"),
            new pig(1090, height / 2 + 80, 0.3f, ground, "pig3.png"),
            new pig(950, height / 2 - 400, 0.3f, ground, "pig3.png")

        };
    }

    private void createUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        createTopBar(root);
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
                game.setScreen(new PauseMenu(game, level));
                dispose();
            }
        });
        return pauseButton;
    }


    private void initializePigs() {
        pigs = new pig[]{
            new pig(1490, height / 2 - 160, 0.3f, ground, "pig1.png"),
            new pig(1640, height / 2 - 240, 0.3f, ground, "pig1.png")
        };
    }

private boolean checkPigCollision(Bird bird, pig pig) {
    if (pig == null) return false;

    return bird.getX() < pig.getX() + pig.getWidth() &&
        bird.getX() + bird.getWidth() > pig.getX() &&
        bird.getY() < pig.getY() + pig.getHeight() &&
        bird.getY() + bird.getHeight() > pig.getY();
}

    public void destroyPig(int pigIndex) {
        try {
            if (pigIndex >= 0 && pigIndex < pigs.length && pigs[pigIndex] != null) {
                pigs[pigIndex].dispose();
                pigs[pigIndex].markAsDead();
                checkLevelCompletion();
            }
        } catch (Exception e) {
            System.err.println("Error destroying pig: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkLevelCompletion() {
        boolean allPigsDestroyed = true;
        for (pig pig : pigs) {
            if (pig != null && !pig.isDead()) {
                allPigsDestroyed = false;
                System.out.println("Pig is alive at position: " + pig.getX() + ", " + pig.getY());
                break;
            }
        }
        boolean allBirdsExhausted = true;
        for (Bird bird : birds) {
            if (!bird.isExhausted()) {
                allBirdsExhausted = false;
                System.out.println("Bird is not exhausted. Remaining birds: " + bird.getX() + ", " + bird.getY());
                break;
            }
            else {
                allBirdsExhausted = true;
            }
        }
        System.out.println("All pigs destroyed: " + allPigsDestroyed);
        System.out.println("All birds exhausted: " + allBirdsExhausted);

        if (allPigsDestroyed) {
            game.setScreen(new VictoryMenu(game));
            dispose();
        }
        else if (allBirdsExhausted && !allPigsDestroyed) {
            game.setScreen(new FailMenu(game, level));
            dispose();
        }
    }

    private void drawTrajectory(SpriteBatch batch, float startX, float startY, float xVelocity, float yVelocity, TextureRegion birdImage) {

        float xProjectile = startX;
        float yProjectile = startY;

        float gravity = 0.02f;
        float velocityMultiplier = 0.38f;
        xVelocity *= velocityMultiplier;
        yVelocity *= velocityMultiplier;

        batch.begin();

        int i = 0;
        while (i <= 170 && xProjectile >= 0 && xProjectile <= ground.getWidth() && yProjectile > ground.getHeight()) {
            if (i % 3 == 0) {
                batch.draw(birdImage, xProjectile, yProjectile, 5, 5);
            }
            i++;

            xProjectile += xVelocity;
            yProjectile += yVelocity;

            yVelocity -= gravity;
        }

        batch.end();
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

        for (Bird bird : birds) {
            try {
                bird.draw(game.batch);

                if (bird == currentBird && birdLaunched) {
                    bird.move(deltaTime);

                    if (bird instanceof Black) {
                        Black blackBird = (Black) bird;
                        blackBird.renderExplosion(game.batch);
                        if (blackBird.shouldTriggerExplosion() && !blackBird.hasExploded()) {
                            blackBird.explode(obstacles, pigs);
                            blackBird.resetExplosionFlag();
                        }
                    }

                    for (BaseObstacle obstacle : obstacles) {
                        if (obstacle.isHitByBird(bird)) {
                            float pushX = (bird.getXVelocity() * 15f);
                            float pushY = (bird.getYVelocity() * 15f);
                            obstacle.push(pushX, pushY);
                            obstacle.update(deltaTime);
                            obstacle.crumble();
                            obstacle.onCollision(bird);
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
                        if (currentBirdIndex >= birds.length) {
                            checkLevelCompletion();
                        }
                    }
                }
            } catch (BirdOutOfScreenException ex) {
                bird.setXVelocity(0);
                bird.setYVelocity(0);
                bird.isExhausted();
                currentBirdIndex++;
                setupNextBird();
                if (currentBirdIndex >= birds.length) {
                    checkLevelCompletion();
                }
            }
        }

        for (BaseObstacle obstacle : obstacles) {
            obstacle.update(deltaTime);
            obstacle.draw(game.batch);
            obstacle.checkPigContact(pigs);
        }

        for (pig pig : pigs) {
            if (pig != null) {
                pig.update(deltaTime);
                pig.draw(game.batch);
            }
        }

        game.batch.end();

        renderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0f, 1f, 0f, 1f);
        ground.draw(renderer);
        renderer.end();

        if (isDragging && currentBird != null && !birdLaunched) {
            Gdx.gl.glEnable(GL20.GL_BLEND);

            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(1, 0, 0, 1);

            float xVelocity = (dragStartX - dragEndX) * 0.25f;
            float yVelocity = (dragStartY - dragEndY) * 0.25f;

            drawTrajectory(game.batch, currentBird.getX(), currentBird.getY(), xVelocity, yVelocity, currentBird.getImage());

            renderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        stage.act();
        stage.draw();
    }



    private float calculateDistanceTraveled(Bird bird) {
        return Math.abs(bird.getX() - 200);
    }

    private boolean checkCollision(Bird bird, BaseObstacle obstacle) {
        return bird.getX() < obstacle.getX() + obstacle.getWidth() &&
            bird.getX() + bird.getWidth() > obstacle.getX() &&
            bird.getY() < obstacle.getY() + obstacle.getHeight() &&
            bird.getY() + bird.getHeight() > obstacle.getY();
    }




    public ArrayList<Object> predictTrajectory(float initialX, float initialY, float initialXVelocity, float initialYVelocity, float deltaTime, float maxTime) {
        ArrayList<Object> trajectoryPoints = new ArrayList<>();

        float x = initialX;
        float y = initialY;
        float xVelocity = initialXVelocity;
        float yVelocity = initialYVelocity;

        for (float t = 0; t <= maxTime; t += deltaTime) {
            trajectoryPoints.add(new Vector2(x, y));

            x += xVelocity * deltaTime;
            y += yVelocity * deltaTime;

            yVelocity -= Ground.GRAVITY * deltaTime;

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
        for (BaseObstacle obstacle : obstacles) {
            obstacle.dispose();
        }
        for (pig pig : pigs) {
            pig.dispose();
        }
        for (Bird bird : birds) {
            if (bird.getImage() != null && bird.getImage().getTexture() != null) {
                bird.getImage().getTexture().dispose();
            }
        }
        skin.dispose();
        stage.dispose();
        background.dispose();
    }

}
