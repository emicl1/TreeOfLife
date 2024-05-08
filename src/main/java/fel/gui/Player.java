package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import fel.jsonFun.PlayerConfig;
import fel.jsonFun.PlayerLoader;
import org.slf4j.Logger;

public class Player {

    public World world;
    public Body playerBody;
    public float x;
    public float y;
    public float speed = 80;
    public float jumpSpeed = 55;
    public float boxWidth;
    public float boxHeight;
    public float PPM = 160;
    public float density = 2f;
    public float swordWidth;
    public float swordHeight;

    public Sword sword;
    public boolean hasSword = false;

    public Animation<TextureRegion> walkAnimation;
    public Animation<TextureRegion> standingAnimation;
    public Animation<TextureRegion> walkingWithSwordAnimation;
    public Animation<TextureRegion> standingWithSwordAnimation;
    public Animation<TextureRegion> AttackAnimation;
    PlayerConfig config;

    public Logger log;


    public Player(World world, float x, float y, String PlayerConfigPath, Logger log) {
        this.world = world;
        this.x = x;
        this.y = y;
        makeConfig(PlayerConfigPath);
        loadPlayerAnimations(config);
        this.speed = config.speed;
        this.jumpSpeed = config.jumpSpeed;
        this.boxWidth = config.boxWidth;
        this.boxHeight = config.boxHeight;
        this.PPM = config.PPM;
        this.density = config.density;
        this.swordWidth = config.swordWidth;
        this.swordHeight = config.swordHeight;
        this.log = log;
        createPlayer();
    }


    private void makeConfig(String PlayerConfigPath){
        PlayerLoader playerLoader = new PlayerLoader();
        config = playerLoader.loadPlayer(PlayerConfigPath);
    }



    public void createPlayer(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        playerBody = world.createBody(bodyDef);
        log.info("Player body created at: " + x + " " + y);
        log.info("Box width and height:" + boxWidth + " " + boxHeight);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth, boxHeight);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;

        fixtureDef.friction = 0.4f;

        Fixture playerFixture = playerBody.createFixture(fixtureDef);
        playerFixture.setUserData("player");
        shape.dispose();

        playerBody.setUserData(this);
        playerBody.setActive(true);

    }

    public void loadWalkingAnimation(PlayerConfig config){
        Array<TextureRegion> frames = new Array<>();
        for (String walkingPath : config.walking){
            frames.add(new TextureRegion(new Texture(walkingPath)));
        }
        walkAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    public void loadStandingAnimation(PlayerConfig config){
        Array<TextureRegion> frames = new Array<>();
        for (String standingPath : config.standing){
            frames.add(new TextureRegion(new Texture(standingPath)));
        }
        standingAnimation = new Animation<>(0.3f, frames, Animation.PlayMode.LOOP);
    }

    public void loadWalkingWithSwordAnimation(PlayerConfig config){
        Array<TextureRegion> frames = new Array<>();
        for (String walkingWithSwordPath : config.walkingWithSword){
            frames.add(new TextureRegion(new Texture(walkingWithSwordPath)));
        }
        walkingWithSwordAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    public void loadStandingWithSwordAnimation(PlayerConfig config){
        Array<TextureRegion> frames = new Array<>();
        for (String standingWithSwordPath : config.standingWithSword){
            frames.add(new TextureRegion(new Texture(standingWithSwordPath)));
        }
        standingWithSwordAnimation = new Animation<>(0.3f, frames, Animation.PlayMode.LOOP);
    }

    public void loadAttackAnimation(PlayerConfig config){
        Array<TextureRegion> frames = new Array<>();
        for (String attackPath : config.attacking){
            frames.add(new TextureRegion(new Texture(attackPath)));
        }
        AttackAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    public void loadPlayerAnimations(PlayerConfig config){
        loadWalkingAnimation(config);
        loadStandingAnimation(config);
        loadWalkingWithSwordAnimation(config);
        loadStandingWithSwordAnimation(config);
        loadAttackAnimation(config);
    }

    private TextureRegion getCurrentFrame(float stateTime, boolean isMoving, boolean hasSword, boolean isAttacking){

        TextureRegion currentFrame;
        if (isAttacking){
            currentFrame = AttackAnimation.getKeyFrame(stateTime);
        } else if (isMoving){
            if (hasSword){
                currentFrame = walkingWithSwordAnimation.getKeyFrame(stateTime);
            } else {
                currentFrame = walkAnimation.getKeyFrame(stateTime);
            }
        } else {
            if (hasSword){
                currentFrame = standingWithSwordAnimation.getKeyFrame(stateTime);
            } else {
                currentFrame = standingAnimation.getKeyFrame(stateTime);
            }
        }
        return currentFrame;
    }

    public void drawPlayer(SpriteBatch batch, float stateTime, boolean isMoving, boolean hasSword, boolean isAttacking, boolean isFacingRight){
        TextureRegion currentFrame = getCurrentFrame(stateTime, isMoving, hasSword, isAttacking);
        float playerX = playerBody.getPosition().x - currentFrame.getRegionWidth() * 0.5f / PPM;
        float playerY = playerBody.getPosition().y - currentFrame.getRegionHeight() * 0.5f / PPM;
        float width = currentFrame.getRegionWidth() / PPM;
        float height = currentFrame.getRegionHeight() / PPM + 0.5f;

        if (!isFacingRight) {
            batch.draw(currentFrame, playerX + width, playerY, -width, height);
        } else {
            batch.draw(currentFrame, playerX, playerY, width, height);
        }

    }


    public Vector2 getPosition(){
        return playerBody.getPosition();
    }

    public void jump(){
        playerBody.applyLinearImpulse(0, jumpSpeed, playerBody.getWorldCenter().x, playerBody.getWorldCenter().y, true);
    }

    public void moveLeft(){
        playerBody.applyForceToCenter(-speed, 0, true);
    }

    public void moveRight(){
        playerBody.applyForceToCenter(speed, 0, true);
    }

    public void initSword(){
        sword = new Sword(playerBody.getPosition().x, playerBody.getPosition().y);
        sword.createAndAttachSword(world, playerBody, swordWidth, swordHeight, boxHeight);
        hasSword = true;
        log.info("Sword created and attached");
    }

    public void attack() {
        if (sword != null) {
            float currentAngle = sword.getAngle() % (2 * MathUtils.PI); // Normalize the angle

            float targetAngle = 179 * MathUtils.degreesToRadians;

            if (Math.abs(currentAngle) < targetAngle) {
                sword.update(); // Continue to apply torque
            }

            // Once the sword rotates slightly more than 179 degrees, dispose of it
            if (Math.abs(currentAngle) >= targetAngle) {
                sword.dispose(); // Dispose of the sword and remove it
                sword = null; // Make sure to nullify the reference
            }
        } else {
            log.warn("No sword to attack with");
        }
    }

    public void dispose(){
        for (TextureRegion region : walkAnimation.getKeyFrames()){
            region.getTexture().dispose();
        }
        for (TextureRegion region : standingAnimation.getKeyFrames()){
            region.getTexture().dispose();
        }
        for (TextureRegion region : walkingWithSwordAnimation.getKeyFrames()){
            region.getTexture().dispose();
        }
        for (TextureRegion region : standingWithSwordAnimation.getKeyFrames()){
            region.getTexture().dispose();
        }
        for (TextureRegion region : AttackAnimation.getKeyFrames()){
            region.getTexture().dispose();
        }
    }

}
