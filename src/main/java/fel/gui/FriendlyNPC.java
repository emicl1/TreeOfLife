package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import fel.jsonFun.FriendlyNPCConfig;
import org.slf4j.Logger;

/**
 * Represents a friendly NPC in the game in GUI
 * The NPC can interact with the player
 * by giving hints or items
 * can be extended by object because it differs just in
 * animations
 */

public class FriendlyNPC extends Object{
    private Animation<TextureRegion> Animation;
    private World world;
    private Body body;
    private String name;
    private String [] pathToAnimations;
    public float x;
    public float y;

    public FriendlyNPC(FriendlyNPCConfig config, Logger log) {
        super(log);
        this.pathToAnimations = config.pathToAnimations;
        this.name = config.name;
        this.x = config.x;
        this.y = config.y;
        this.width = config.boxWidth;
        this.height = config.boxHeight;
    }

    @Override
    public void createBody(World world) {
        this.world = world;
        BodyDef bodyDef = makeBodyDef(false);
        this.body = world.createBody(bodyDef);
        body.setTransform(new Vector2(x, y), 0);
        shape = new PolygonShape();
        shape.setAsBox(width, height);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();

    }

    /**
     * Load the animations for the NPC
     */
    public void loadAnimations() {
        log.info("Loading animations for: " + name);
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < pathToAnimations.length; i++) {
            try {
                TextureRegion textureRegion = new TextureRegion(new Texture(pathToAnimations[i]));
                frames.add(textureRegion);
            } catch (Exception e) {
                log.info("Error loading animation: " + pathToAnimations[i]);
            }
        }
        Animation = new Animation(1f/3f, frames, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
    }

    /**
     * Draw the NPC
     * @param batch The sprite batch
     * @param stateTime The time
     * @param PPM The pixels per meter
     */
    public void draw(SpriteBatch batch, float stateTime, float PPM){
        TextureRegion currentFrame = Animation.getKeyFrame(stateTime);
        float npcX = body.getPosition().x - currentFrame.getRegionWidth() * 0.5f / PPM;
        float npcY = body.getPosition().y - currentFrame.getRegionHeight() * 0.5f / PPM;
        batch.draw(currentFrame, npcX, npcY, currentFrame.getRegionWidth() / PPM, currentFrame.getRegionHeight() / PPM);
    }


    @Override
    public void dispose() {
        world.destroyBody(body);
        //dispose animations
        for (TextureRegion textureRegion : Animation.getKeyFrames()) {
            textureRegion.getTexture().dispose();
        }
    }

    public String getName(){
        return name;
    }

}
