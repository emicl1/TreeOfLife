package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import org.slf4j.Logger;

public class Object {
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

    public Object(Logger log) {
        this.log = log;
    }

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

    public void createBody(World world) {
        this.world = world;
        BodyDef bodyDef = makeBodyDef(true);
        this.body = world.createBody(bodyDef);
        FixtureDef fixtureDef = makeFixtureDef(true, 1, false);
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

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
