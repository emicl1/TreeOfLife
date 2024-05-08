package fel.gui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class FriendlyNPC {
    public Animation<TextureRegion> Animation;
    public World world;
    public Body body;
    public String name;
    public String [] pathToAnimations;
    public float boxWidth = 1.6f;
    public float boxHeight = 0.95f;
    public float x;
    public float y;

    //TODO make it so that it extends the object class i think
    // it's the way to go

    public FriendlyNPC(World world, String name, String [] pathToAnimations, float x, float y, float boxWidth, float boxHeight) {
        this.pathToAnimations = pathToAnimations;
        this.name = name;
        this.world = world;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox( boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();


        body.setActive(true);
    }



}
