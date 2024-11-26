package com.cantyouc.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.cantyouc.angrybirds.bird.Bird;

import java.util.ArrayList;

public class Obstacle {
    private Texture texture;  // This resolves the issue with the texture symbol
    private float x, y;
    private float width, height;
    private float xVelocity, yVelocity; // For pushing the obstacle
    private ArrayList<Piece> pieces;
    private boolean isCrumbling = false;

    public Obstacle(float x, float y, String path) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(path);  // Loads the texture
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.pieces = new ArrayList<>();
    }

    public void update(float deltaTime) {
        if (pieces.isEmpty()) {
            // Gravity effect on the obstacle
            this.x += xVelocity * deltaTime;
            this.y += yVelocity * deltaTime;
        } else {
            // Update each piece of the crumbled obstacle
            for (Piece piece : pieces) {
                piece.update(deltaTime);
            }
        }
    }


    public void draw(SpriteBatch batch) {
        if (pieces.isEmpty()) {
            // Draw the original obstacle
            batch.draw(texture, x, y);
        } else {
            // Draw each piece of the crumbled obstacle
            for (Piece piece : pieces) {
                piece.draw(batch);
            }
        }
    }

    // Set new velocity when the obstacle is pushed
    public void push(float vx, float vy) {
        this.xVelocity = vx;
        this.yVelocity = vy;
    }

    // Getters for collision detection
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    // Check if the obstacle is hit by the bird
    public boolean isHitByBird(Bird bird) {
        float birdX = bird.getX();
        float birdY = bird.getY();
        float birdWidth = bird.getWidth();
        float birdHeight = bird.getHeight();

        // Check if the bird's bounding box intersects with the obstacle
        return (birdX < this.x + this.width && birdX + birdWidth > this.x &&
            birdY < this.y + this.height && birdY + birdHeight > this.y);
    }

    // Split the obstacle into pieces upon collision
    // Method to split the obstacle into random parts
    public void crumble() {
        pieces.clear(); // Clear any previous pieces
        isCrumbling = true;

        // Define number of pieces to break the obstacle into
        int numPieces = 20; // Number of pieces (can adjust as needed)

        // Randomly cut the texture into pieces
        for (int i = 0; i < numPieces; i++) {
            float randomX = MathUtils.random(0, width - 20); // Random x position of the piece
            float randomY = MathUtils.random(0, height - 20); // Random y position of the piece
            float randomWidth = MathUtils.random(10, 50); // Random width of the piece
            float randomHeight = MathUtils.random(10, 50); // Random height of the piece

            // Ensure that the piece is within the bounds of the obstacle texture
            if (randomX + randomWidth > width) randomWidth = width - randomX;
            if (randomY + randomHeight > height) randomHeight = height - randomY;
            TextureRegion region = new TextureRegion(texture, (int) randomX, (int) randomY, (int) randomWidth, (int) randomHeight);

            // Create a piece and add it to the list
            Piece piece = new Piece(region, x + randomX, y + randomY, randomWidth, randomHeight,
                MathUtils.random(-50, 50), MathUtils.random(-50, 50));
            pieces.add(piece);
        }
    }

    public void checkPigContact(pig[] pigs) {
        if (isCrumbling) {
            // Check for pigs in contact with the crumbling obstacle
            for (pig pig : pigs) {
                if (pig != null && !pig.isDead() && pig.isInContactWithObstacle(this)) {
                    // If a pig is in contact with the crumbling obstacle, apply gravity or force
                    pig.applyForce(-50);  // Apply a downward force to simulate falling
                }
            }
        }
    }

    // Dispose texture when done
    public void dispose() {
        texture.dispose();
    }

    private class Piece {
        private TextureRegion pieceTexture;
        private float x, y, width, height;
        private float xVelocity, yVelocity;

        public Piece(TextureRegion texture, float x, float y, float width, float height, float xVelocity, float yVelocity) {
            this.pieceTexture = texture; // Create a piece from the texture
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
        }

        public void update(float deltaTime) {
            this.x += xVelocity * deltaTime;
            this.y += yVelocity * deltaTime;
            this.yVelocity -= 9.8f * deltaTime;  // Apply gravity to each piece
        }

        public void draw(SpriteBatch batch) {
            batch.draw(pieceTexture, x, y, width, height); // Draw piece of the texture
        }
    }
}
