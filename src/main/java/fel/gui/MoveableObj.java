package fel.gui;




import org.slf4j.Logger;
import com.badlogic.gdx.physics.box2d.*;
import fel.jsonFun.MoveableObjConfig;

public class MoveableObj extends Object{

    public float density;

    public MoveableObj(MoveableObjConfig config, Logger log) {
        super(log);
        this.path = config.path;
        this.x = config.x;
        this.y = config.y;
        this.width = config.width;
        this.height = config.height;
        this.density = config.density;
    }

    public void createBody(World world) {
        this.world = world;

        BodyDef bodyDef = makeBodyDef(true);
        this.body = world.createBody(bodyDef);

        FixtureDef fixtureDef = makeFixtureDef(true, density, true);

        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

}
