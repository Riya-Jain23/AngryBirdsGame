package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.cantyouc.angrybirds.bird.Bird;
import java.util.ArrayList;

public abstract class BaseObstacle {
    protected float x, y;
    private ArrayList<Piece> pieces;
    public float xVelocity;
    private float yVelocity;
    protected float width, height;
    protected Texture texture;
    protected float hardness;
    protected float weight;
    public boolean isCrumbling = false;

    public BaseObstacle(float x, float y, String texturePath, float hardness, float weight) {
        this.x = x;
        this.y = y;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.texture = new Texture(texturePath);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.hardness = hardness;
        this.weight = weight;
        this.pieces = new ArrayList<>();
    }

    public abstract void update(float deltaTime);
    public abstract void draw(SpriteBatch batch);
    public abstract void onCollision(Bird bird);

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public void checkPigContact(pig[] pigs) {
        if (isCrumbling) {

            for (pig pig : pigs) {
                if (pig != null && !pig.isDead() && pig.isInContactWithObstacle(this)) {

                    pig.applyForce(-100);
                }
            }
        }
    }
    public void push(float vx, float vy) {
        this.xVelocity = vx;
        this.yVelocity = vy;
    }
    public void crumble() {
        pieces.clear();
        isCrumbling = true;

        int numPieces = 5;

        for (int i = 0; i < numPieces; i++) {
            float randomX = MathUtils.random(0, width - 20);
            float randomY = MathUtils.random(0, height - 20);
            float randomWidth = MathUtils.random(10, 50);
            float randomHeight = MathUtils.random(10, 50);

            if (randomX + randomWidth > width) randomWidth = width - randomX;
            if (randomY + randomHeight > height) randomHeight = height - randomY;
            TextureRegion region = new TextureRegion(texture, (int) randomX, (int) randomY, (int) randomWidth, (int) randomHeight);

            Piece piece = new Piece(region, x + randomX, y + randomY, randomWidth, randomHeight,
                MathUtils.random(-50, 50), MathUtils.random(-50, 50));
            pieces.add(piece);
        }
    }
    public boolean isHitByBird(Bird bird) {
        float birdX = bird.getX();
        float birdY = bird.getY();
        float birdWidth = bird.getWidth();
        float birdHeight = bird.getHeight();

        return (birdX < this.x + this.width && birdX + birdWidth > this.x &&
            birdY < this.y + this.height && birdY + birdHeight > this.y);
    }
    public float getxVelocity() {
        return xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    public void bend(float angle){

    }


    public boolean isCrumbling() {
        return isCrumbling;
    }
}
