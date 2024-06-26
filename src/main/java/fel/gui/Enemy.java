package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;


/**
 * Represents an enemy in the game in GUI
 * The enemy can patrol between two points or follow the player
 */
public class Enemy {
    public Animation<TextureRegion> walkAnimation;

    public Body body;
    private String name;
    public String currentState;
    private float leftBoundary;
    private float rightBoundary;
    private String[] pathToAnimations;
    public boolean isFacingRight = true;

    private float speedPatrol = 2.0f;
    private float speedFollow = 4.0f;
    private float speedPatrolOriginal = 2.0f;
    private float speedFollowOriginal = 4.0f;


    private float boxWidth = 1.6f;
    private float boxHeight = 0.95f;

    public Enemy(World world, String name, String[] pathToAnimations, Vector2 startPosition, float leftBoundary, float rightBoundary) {
        this.pathToAnimations = pathToAnimations;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.name = name;
        this.currentState = "PATROLLING";
        //Create the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosition);
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();

        body.setActive(true);
    }

    public Enemy(World world, String name, String[] pathToAnimations, Vector2 startPosition, float leftBoundary, float rightBoundary, float boxWidth, float boxHeight) {
        this.pathToAnimations = pathToAnimations;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.currentState = "PATROLLING";
        this.name = name;
        //Create the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosition);
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();

        body.setActive(true);
    }

    public Enemy(World world, String name, String[] pathToAnimations, Vector2 startPosition, float leftBoundary, float rightBoundary, float boxWidth, float boxHeight, float speedPatrol, float speedFollow) {
        this.pathToAnimations = pathToAnimations;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.name = name;
        this.currentState = "PATROLLING";
        this.speedPatrolOriginal = speedPatrol;
        this.speedFollowOriginal = speedFollow;
        //Create the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosition);
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();

        body.setActive(true);
    }

    /**
     * Loads the enemy's animation
     */
    public void loadAnimationEnemy() {
        Array<TextureRegion> frames = new Array<>();

        for (String path : pathToAnimations) {
            Texture texture = new Texture(path);
            frames.add(new TextureRegion(texture));
        }

        walkAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    /**
     * Updates the enemy's position and state
     *
     * @param playerPosition The player's position
     */
    public void update(Vector2 playerPosition) {
        checkIfPlayerIsInBoundery(playerPosition);

        if (Objects.equals(currentState, "PATROLLING")) {
            updatePatrolling();

        } else if (Objects.equals(currentState, "FOLLOWING")) {
            updateFollowing(playerPosition);
        }
    }

    private void updatePatrolling() {
        if (body.getPosition().x <= leftBoundary) {
            speedPatrol = speedPatrolOriginal;
            isFacingRight = true;
        } else if (body.getPosition().x >= rightBoundary) {
            speedPatrol = -1 * speedPatrolOriginal;
            isFacingRight = false;
        }
        body.setLinearVelocity(speedPatrol, body.getLinearVelocity().y);
    }

    private void updateFollowing(Vector2 playerPosition) {
        if (playerPosition.x < body.getPosition().x) {
            speedFollow = -1 * speedFollowOriginal; // Ensure the enemy moves towards the player
            isFacingRight = false;
        } else {
            speedFollow = speedFollowOriginal;
            isFacingRight = true;
        }
        body.setLinearVelocity(speedFollow, body.getLinearVelocity().y);
    }

    /**
     * Draws the enemy
     *
     * @param batch     The sprite batch to draw the enemy
     * @param stateTime The time the enemy has been in its current state
     */
    public void draw(SpriteBatch batch, float stateTime) {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime);
        float PPM = 250f; // Pixels per meter for drawing
        float playerX = body.getPosition().x - currentFrame.getRegionWidth() * 0.5f / PPM;
        float playerY = body.getPosition().y - currentFrame.getRegionHeight() * 0.5f / PPM;
        float width = currentFrame.getRegionWidth() / PPM;
        float height = currentFrame.getRegionHeight() / PPM + 0.5f;

        if (!isFacingRight) {
            batch.draw(currentFrame, playerX + width, playerY, -width, height);
        } else
            batch.draw(currentFrame, playerX, playerY, width, height);
    }

    private void setState(String state) {
        this.currentState = state;
    }

    private void checkIfPlayerIsInBoundery(Vector2 playerPosition) {
        if (playerPosition.x > leftBoundary && playerPosition.x < rightBoundary) {
            setState("FOLLOWING");
        } else {
            if (speedPatrol < 0) {
                isFacingRight = false;
            } else {
                isFacingRight = true;
            }
            setState("PATROLLING");
        }
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public String getName() {
        return name;
    }

    public void die() {
        body.setActive(false);
    }

    public void dispose() {
        for (String path : pathToAnimations) {
            Texture texture = new Texture(path);
            texture.dispose();
        }

    }


}

