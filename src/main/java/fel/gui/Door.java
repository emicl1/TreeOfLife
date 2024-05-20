package fel.gui;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import fel.jsonFun.DoorConfig;
import org.slf4j.Logger;

/**
 * Door class that extends Object class
 * This class is used to create a door object in the game
 * which can be opened by pressing a button
 */

public class Door extends GameObject {

    public Door(DoorConfig config, Logger log) {
        super(log);
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

    /**
     * Method to open the door by setting the body to active
     */
    public void open() {
        body.setActive(true);
    }

    /**
     * Method to close the door by setting the body to inactive
     */
    public void close() {
        body.setActive(false);
    }
}
