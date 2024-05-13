package fel.gui;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import fel.jsonFun.ButtonConfig;
import fel.jsonFun.DoorConfig;
import org.slf4j.Logger;

/**
 * Button class that extends Object class
 * This class is used to create a button object in the game
 * which can be pressed by the player or dynamic object to open doors
 */
public class Button extends Object{

    private Array<Door> doors;
    public boolean isNotPressed = false;
    public Logger log;

    public Button(ButtonConfig config, Logger log) {
        super(log);
        this.path = config.path;
        this.x = config.x;
        this.y = config.y;
        this.width = config.width;
        this.height = config.height;
        this.doors = new Array<Door>();
        this.log = log;
        for (DoorConfig doorConfig : config.doors) {
            this.doors.add(new Door(doorConfig, log));
        }

    }

    @Override
    public void createBody(World world) {

        this.world = world;

        BodyDef bodyDef = makeBodyDef(false);
        this.body = world.createBody(bodyDef);

        FixtureDef fixtureDef = makeFixtureDef(false, 1, true);

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
        createDoors(world);
    }

    public Array<Door> getDoors() {
        return doors;
    }


    private void createDoors(World world) {
        if (doors == null) {
            return;
        }
        for (Door door : doors) {
            System.out.println("Creating door");
            door.loadSprite();
            door.createBody(world);
        }
    }

    @Override
    public void dispose() {

        sprite.getTexture().dispose();
        world.destroyBody(body);
        for (Door door : doors) {
            door.dispose();
        }
    }
}
