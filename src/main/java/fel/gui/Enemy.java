package fel.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.swing.*;
import java.util.Objects;

public class Enemy extends Actor {
    private Animation<TextureRegion> walkAnimation;

    private Body body;
    private String currentState;
    private float leftBoundary;
    private float rightBoundary;
    private String [] pathToAnimations;

    private float speedPatrol = 2.0f;
    private float speedFollow = 4.0f;

    private Vector2 startPosition;
    private World world;

    public Enemy(World world, String [] pathToAnimations, Vector2 startPosition, float leftBoundary, float rightBoundary) {
        this.pathToAnimations = pathToAnimations;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.currentState = "PATROLLING";
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosition);
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox( 1.6f, 0.95f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
        body.setActive(true);

        Texture enemyTexture = new Texture("eastwoods/mnbrouk2.png");
        Sprite enemySprite = new Sprite(enemyTexture);
        enemySprite.setSize(1.6f, 0.6f);

    }

    public void createEnemy(){

    }


    public void loadAnimationEnemy() {
        Texture playerTextureMovement1 = new Texture("eastwoods/mnbrouk1.png");
        Texture playerTextureMovement2 = new Texture("eastwoods/mnbrouk2.png");
        Texture playerTextureMovement3 = new Texture("eastwoods/mnbrouk3.png");
        Texture playerTextureMovement4 = new Texture("eastwoods/mnbrouk4.png");

        Array<TextureRegion> frames = new Array<>();
        frames.add(new TextureRegion(playerTextureMovement1));
        frames.add(new TextureRegion(playerTextureMovement2));
        frames.add(new TextureRegion(playerTextureMovement3));
        frames.add(new TextureRegion(playerTextureMovement4));
        walkAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    public void update( Vector2 playerPosition) {

        checkIfPlayerIsInBoundery(playerPosition);

        if (Objects.equals(currentState, "PATROLLING")){
            updatePatrolling();

        } else if (Objects.equals(currentState, "FOLLOWING"))  {
            updateFollowing(playerPosition);
        }
    }

    private void updatePatrolling() {
        if (body.getPosition().x <= leftBoundary ) {
            speedPatrol = 2f;
        } else if (body.getPosition().x >= rightBoundary) {
            speedPatrol = -2f;
        }
        body.setLinearVelocity(speedPatrol, body.getLinearVelocity().y);
    }

    private void updateFollowing(Vector2 playerPosition) {
        if (playerPosition.x < body.getPosition().x) {
            speedFollow = -4f; // Ensure the enemy moves towards the player
        }else {
            speedFollow = 4f;
        }
        body.setLinearVelocity(speedFollow, body.getLinearVelocity().y);
    }

    public void draw(SpriteBatch batch, float stateTime) {

        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime);

        Texture texture = new Texture(Gdx.files.internal("eastwoods/mnbrouk2.png"));
        float PPM = 250f; // Pixels per meter for drawing
        float playerX = body.getPosition().x - currentFrame.getRegionWidth() * 0.5f / PPM;
        float playerY = body.getPosition().y - currentFrame.getRegionHeight() * 0.5f / PPM;
        float width = currentFrame.getRegionWidth() / PPM;
        float height = currentFrame.getRegionHeight() / PPM + 0.5f;
        batch.draw(texture, 0 ,0 );
        batch.draw(currentFrame, playerX, playerY, width, height);
    }

    public void setState(String state) {
        this.currentState = state;
    }

    public void checkIfPlayerIsInBoundery(Vector2 playerPosition) {
        if (playerPosition.x > leftBoundary && playerPosition.x < rightBoundary) {
            setState("FOLLOWING");
        } else {
            setState("PATROLLING");
        }
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }


    private void die() {
        body.setActive(false);
    }

    public void dispose() {
        for (String path : pathToAnimations) {
            Texture texture = new Texture(path);
            texture.dispose();
        }

    }
}

