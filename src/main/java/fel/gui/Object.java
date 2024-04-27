package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

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

    public void loadSprite() {
        System.out.println("Loading sprite: " + path);
        try {
            Texture texture = new Texture(path);
            this.sprite = new Sprite(texture);
            this.sprite.setPosition(x, y);
            this.sprite.setSize(width, height);
        } catch (Exception e) {
            System.err.println("Failed to load sprite: " + e.getMessage());
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
        System.out.println("Creating fixture def");
        if (sprite == null) {
            System.err.println("Sprite is null");
            return null;
        }
        if (isDynamic) {
            shape = new PolygonShape();
            shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = density;

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

        if (sprite != null && body != null) {
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
            sprite.draw(batch);

        }

    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
