package com.badlogic.angrybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
public class AngryBirds extends ApplicationAdapter {
    ShapeRenderer tankRenderer, groundRenderer;
    private OrthographicCamera camera;
    private Ground ground;
    private Bird tank1, tank2;
    private SpriteBatch batch;
    private Texture bgTexture;
    private Texture vsTexture;
    @Override
    public void create () {
        tankRenderer = new ShapeRenderer();
        groundRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        bgTexture = new Texture("background.png");
        vsTexture = new Texture("vs.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1280,720);
        ground = new Ground(1280);
        tank1 = new Bird(100, 20, 16, 20);
        tank2 = new Bird(900, 20, 16, 20);
    }
    @Override
    public void render () {
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bgTexture, 0, 0);
        batch.draw(vsTexture, 640 - 50, 720 - 100);
        batch.end();
        tankRenderer.setProjectionMatrix(camera.combined);
        groundRenderer.setProjectionMatrix(camera.combined);
        tankRenderer.setColor(0,0,1,1);
        groundRenderer.setColor(0,1,0,1);
        tankRenderer.begin(ShapeRenderer.ShapeType.Filled);
        tank1.draw(tankRenderer);
        tank2.draw(tankRenderer);
        tankRenderer.end();

        groundRenderer.begin(ShapeRenderer.ShapeType.Filled);
        ground.draw(groundRenderer);
        groundRenderer.end();
    }

    @Override
    public void dispose () {
        tankRenderer.dispose();
        groundRenderer.dispose();
    }
}
