package fel.gui;


import com.badlogic.gdx.physics.box2d.*;
import fel.jsonFun.ItemConfig;

import org.slf4j.Logger;

/**
 * Item class that extends Object class
 * This class is used to create an item object in the game
 * which can be collected by the player used for crafting or other purposes
 */
public class Item extends GameObject {

    public final String name;
    public boolean isCollectable = false;


    public Item(ItemConfig config, Logger log) {
        super(log);
        this.path = config.path;
        this.name = config.name;
        this.x = config.x;
        this.y = config.y;
        this.width = config.width;
        this.height = config.height;
        this.isCollectable = config.isCollectable;
    }

    @Override
    public void createBody(World world) {

        this.world = world;

        BodyDef bodyDef = makeBodyDef(false);
        this.body = world.createBody(bodyDef);

        FixtureDef fixtureDef = makeFixtureDef(false, 1, true);

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
        world.destroyBody(body);
    }

    public String getName() {
        return name;
    }


}
