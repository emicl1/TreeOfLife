package fel.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import fel.jsonFun.DoorConfig;

public class Door extends Object{


    public Door(DoorConfig config) {
        this.path = config.path;
        this.x = config.x;
        this.y = config.y;
        this.width = config.width;
        this.height = config.height;
    }

    @Override
    public void createBody(World world) {
        this.world = world;

        BodyDef bodyDef = makeBodyDef(false);
        this.body = world.createBody(bodyDef);

        FixtureDef fixtureDef = makeFixtureDef(false, 1, false);

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    public void open() {
        body.setActive(true);
    }

    public void close() {
        body.setActive(false);
    }

}
