package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import fel.jsonFun.PlayerConfig;
import fel.jsonFun.PlayerLoader;

public class Player {

    public World world;
    public Body playerBody;
    public float x = 15;
    public float y = 2;
    public float speed = 80;
    public float jumpSpeed = 55;
    public float boxWidth = 1.6f;
    public float boxHeight = 0.95f;
    public float PPM = 160;

    public Animation<TextureRegion> walkAnimation;
    public Animation<TextureRegion> standingAnimation;
    public Animation<TextureRegion> walkingWithSwordAnimation;
    public Animation<TextureRegion> standingWithSwordAnimation;
    public Animation<TextureRegion> AttackAnimation;
    PlayerConfig config;


    public Player(World world) {
        this.world = world;
        makeConfig();
        loadPlayerAnimations(config);
        createPlayer();
    }

    public Player(World world, float x, float y) {
        this.world = world;
        this.x = x;
        this.y = y;
        makeConfig();
        loadPlayerAnimations(config);
        createPlayer();
    }



    public Player(World world, float x, float y, float speed, float jumpSpeed) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.jumpSpeed = jumpSpeed;
        makeConfig();
        loadPlayerAnimations(config);
        createPlayer();

    }

    public Player(World world, float x, float y, float speed, float jumpSpeed, float boxWidth, float boxHeight) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.jumpSpeed = jumpSpeed;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        makeConfig();
        loadPlayerAnimations(config);
        createPlayer();

    }

    public Player(World world, float x, float y, float speed, float jumpSpeed, float boxWidth, float boxHeight,  float PPM) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.jumpSpeed = jumpSpeed;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.PPM = PPM;
        makeConfig();
        loadPlayerAnimations(config);
        createPlayer();
    }

    private void makeConfig(){
        PlayerLoader playerLoader = new PlayerLoader();
        config = playerLoader.loadPlayer("player/Player.json");
    }



    public void createPlayer(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        playerBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
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
        standingWithSwordAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
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
