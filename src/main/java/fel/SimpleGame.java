package fel;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SimpleGame extends ApplicationAdapter {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private Body player, ground;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();

        // Create ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 1));
        ground = world.createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(camera.viewportWidth, 1);
        ground.createFixture(groundShape, 0);
        groundShape.dispose();

        // Create player
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyType.DynamicBody;
        playerBodyDef.position.set(new Vector2(15, 5));
        player = world.createBody(playerBodyDef);
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(1, 1);
        FixtureDef playerFixture = new FixtureDef();
        playerFixture.shape = playerShape;
        playerFixture.density = 1;
        playerFixture.friction = 0.5f;
        player.createFixture(playerFixture);
        playerShape.dispose();
    }

    @Override
    public void render() {
        handleInput();
        world.step(1 / 60f, 6, 2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);
        camera.update();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            player.applyLinearImpulse(new Vector2(-0.1f, 0), player.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            player.applyLinearImpulse(new Vector2(0.1f, 0), player.getWorldCenter(), true);
        }
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
