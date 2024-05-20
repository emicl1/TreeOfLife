package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import org.slf4j.Logger;

/**
 * Object class that is used to create objects in the game
 * This is class used mainly to be inherited by other classes
 * to create objects in the game, it contains methods to create
 * body and fixture for the object of different types
 */
public class GameObject {
    public String path;
    public float x;
    public float y;
    public float width;
    public float height;

    public Sprite sprite;
    public PolygonShape shape;

    public Body body;
    public World world;

    public Logger log;

    public GameObject(Logger log) {
        this.log = log;
    }

    /**
     * This method is used to load the sprite from the path
     */
    public void loadSprite() {
        log.info("Loading sprite: " + path);
        try {
            Texture texture = new Texture(path);
            this.sprite = new Sprite(texture);
            this.sprite.setPosition(x, y);
            this.sprite.setSize(width, height);
        } catch (Exception e) {
            log.info("Error loading sprite: " + path);
            this.sprite = null;
        }
    }

    /**
     * This method is used to create the body of the object
     * This method should be overwritten by the child class
     * it help to create the bodies and fixtures efficiently
     * @param world The world in which the object is created
     */
    public void createBody(World world) {
        this.world = world;
        BodyDef bodyDef = makeBodyDef(true);
        this.body = world.createBody(bodyDef);
        FixtureDef fixtureDef = makeFixtureDef(true, 1, false);
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    /**
     * This method is used to create the body definition of the object
     * @param isDynamic boolean to check if the object is dynamic or static
     * @return BodyDef The body definition of the object
     */
    public BodyDef makeBodyDef(boolean isDynamic) {
        BodyDef bodyDef = new BodyDef();
        if (isDynamic) {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(x, y);
        }else {
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(x, y);
        }
        return bodyDef;
    }

    /**
     * This method is used to create the fixture definition of the object
     * @param isDynamic boolean to check if the object is dynamic or static
     * @param density The density of the object
     * @param isSensor boolean to check if the object is a sensor
     * @return FixtureDef The fixture definition of the object
     */
    public FixtureDef makeFixtureDef(boolean isDynamic, float density, boolean isSensor) {
        log.info("Creating fixture def");
        if (sprite == null) {
            log.warn("Sprite is null");
            return null;
        }
        if (isDynamic) {
            shape = new PolygonShape();
            shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = density;
            //fixtureDef.isSensor = isSensor;

            return fixtureDef;
        } else {
            shape = new PolygonShape();
            shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.isSensor = isSensor;

            return fixtureDef;
        }
    }

    /**
     * This method is used to draw the object in the game
     * @param batch The sprite batch to draw the object
     */
    public void draw(SpriteBatch batch) {
        if (sprite != null ) { //&& body != null)
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
            sprite.draw(batch);

        }

    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    public Body getBody() {
        return body;
    }

    public void setActive(){
        body.setActive(true);
    }

    public void setInactive(){
        body.setActive(false);
    }

    public void setPosition(float x, float y){
       body.setTransform(x, y, 0);
    }
}
