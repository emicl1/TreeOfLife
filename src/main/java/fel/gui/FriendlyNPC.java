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

public class FriendlyNPC extends Object{
    public Animation<TextureRegion> Animation;
    public World world;
    public Body body;
    public String name;
    public String [] pathToAnimations;
    public float boxWidth = 1.6f;
    public float boxHeight = 0.95f;
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
        shape = new PolygonShape();
        shape.setAsBox(boxWidth, boxHeight);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0;

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();

    }

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
        Animation = new Animation(1f/3f, frames);
    }

    public void draw(SpriteBatch batch, float stateTime) {
        batch.draw(Animation.getKeyFrame(stateTime), body.getPosition().x, body.getPosition().y, width, height);
    }


    @Override
    public void dispose() {
        world.destroyBody(body);
        //dispose animations
        for (TextureRegion textureRegion : Animation.getKeyFrames()) {
            textureRegion.getTexture().dispose();
        }
    }

}
