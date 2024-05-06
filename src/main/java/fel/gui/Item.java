package fel.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import fel.jsonFun.ItemConfig;


public class Item extends Object{

    public String name;
    public boolean isCollectable = false;


    public Item(ItemConfig config) {
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

    public void setCollectable(boolean collectable) {
        this.isCollectable = collectable;
    }

    public boolean isCollectable() {
        return isCollectable;
    }

    public String getName() {
        return name;
    }


}
