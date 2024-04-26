package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fel.jsonFun.GroundConfig;
import fel.jsonFun.LevelConfig;
import fel.jsonFun.LevelLoader;

import java.util.ArrayList;
import java.util.List;

public class BaseScreen implements Screen {
    public Game game;

    public float x;
    public float y;

    public World world;
    public Box2DDebugRenderer debugRenderer;
    public OrthographicCamera camera;
    public Viewport viewport;
    public Player player;
    public SpriteBatch batch;
    public Sprite backgroundSprite;
    private Texture backgroundTexture;

    private List<Sprite> groundSprites = new ArrayList<>();
    public boolean facingRight = true;

    public float stateTime;

    private boolean isOnGround;

    public boolean isMoving = false;

    private int groundContacts = 0;

    public LevelConfig config;

    public String jsonPath;


    public BaseScreen(Game game, float x, float y, String jsonPath) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.jsonPath = jsonPath;
    }


    public void createBackgroundAndGround(LevelConfig config){
        createBackground(config.background);
        createGround(config.groundImage);
        for (GroundConfig ground : config.ground){
            createGroundFromRectangularShape(world, config.groundImage, ground.x, ground.y, ground.x + ground.width, ground.y + ground.height);
        }
    }


    public void createBackground(String path){
        backgroundTexture = new Texture(path);
        backgroundSprite = new Sprite(backgroundTexture);
    }

    public void createWorld(float gravityX, float gravityY){
        stateTime = 0f;  // Accumulate elapsed time

        world = new World(new Vector2(gravityX, gravityY), true);
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        ContactListener listener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (isFixturePlayer(contact.getFixtureA()) && isFixtureGround(contact.getFixtureB()) ||
                        isFixturePlayer(contact.getFixtureB()) && isFixtureGround(contact.getFixtureA())) {
                    groundContacts++;
                    isOnGround = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (isFixturePlayer(contact.getFixtureA()) && isFixtureGround(contact.getFixtureB()) ||
                        isFixturePlayer(contact.getFixtureB()) && isFixtureGround(contact.getFixtureA())) {
                    groundContacts--;
                    if (groundContacts <= 0) {
                        isOnGround = false;
                        groundContacts = 0;  // Reset to prevent negative counts
                    }
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        };
        world.setContactListener(listener);
    }

    public void createCamera() {
        float w = 30;
        float h = 30 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

        System.out.println(Gdx.graphics.getWidth() + "h:" + Gdx.graphics.getHeight());

        camera = new OrthographicCamera(w, h);
        viewport = new FitViewport(w, h, camera);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        viewport.apply();
        camera.update();
    }



    public void createGround(String path) {
        float groundWidth = 2f;
        float groundHeight = 2f;
        int numGroundSegments = 13;

        Texture groundTexture = new Texture(path);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(groundWidth / 2 , groundHeight / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;

        for (int i = 0; i < numGroundSegments; i++) {
            // Set the position for each ground body
            bodyDef.position.set(i * groundWidth, 1f);

            // Create the ground body
            Body groundBody = world.createBody(bodyDef);
            groundBody.createFixture(fixtureDef);
            Fixture groundFixture = groundBody.createFixture(fixtureDef);
            groundFixture.setUserData("ground");

            // Create and configure the sprite for each ground body
            Sprite groundSprite = new Sprite(groundTexture);
            groundSprite.setSize(groundWidth, groundHeight);
            groundSprite.setPosition(groundBody.getPosition().x - groundWidth / 2, groundBody.getPosition().y - groundHeight / 2);
            groundSprites.add(groundSprite);
        }

        shape.dispose();

    }

    public void createGroundFromRectangularShape(World world, String path, float x1, float y1, float x2, float y2) {
        float groundWidth = 2f;  // Width of each ground segment
        float groundHeight = 2f; // Height of each ground segment
        Texture groundTexture = new Texture(path);

        int numGroundSegmentsX = (int) Math.ceil((x2 - x1) / groundWidth);
        int numGroundSegmentsY = (int) Math.ceil((y2 - y1) / groundHeight);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(groundWidth / 2, groundHeight / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;

        for (int i = 0; i < numGroundSegmentsX; i++) {
            for (int j = 0; j < numGroundSegmentsY; j++) {
                float posX = x1 + i * groundWidth + groundWidth;
                float posY = y1 + j * groundHeight + groundHeight / 2;
                bodyDef.position.set(posX, posY);

                Body groundBody = world.createBody(bodyDef);
                groundBody.createFixture(fixtureDef).setUserData("ground");

                Sprite groundSprite = new Sprite(groundTexture);
                groundSprite.setSize(groundWidth, groundHeight);
                groundSprite.setPosition(posX - groundWidth / 2, posY - groundHeight / 2);
                groundSprites.add(groundSprite);
            }
        }

        shape.dispose();
    }

    public void createWorldBounds(float worldWidth, float worldHeight) {
        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.StaticBody;

        // Horizontal boundary (top and bottom)
        EdgeShape horizontalEdge = new EdgeShape();
        horizontalEdge.set(new Vector2(0, 0), new Vector2(worldWidth, 0));

        // Vertical boundary (left and right)
        EdgeShape verticalEdge = new EdgeShape();
        verticalEdge.set(new Vector2(0, 0), new Vector2(0, worldHeight));

        // Create four boundaries
        FixtureDef fd = new FixtureDef();
        fd.shape = horizontalEdge;
        fd.density = 0;
        fd.friction = 0.3f;

        // Bottom boundary
        bd.position.set(0, 0);
        Body bottomBoundary = world.createBody(bd);
        bottomBoundary.createFixture(fd);

        // Top boundary
        bd.position.set(0, worldHeight);
        Body topBoundary = world.createBody(bd);
        topBoundary.createFixture(fd);

        fd.shape = verticalEdge;

        // Left boundary
        bd.position.set(0, 0);
        Body leftBoundary = world.createBody(bd);
        leftBoundary.createFixture(fd);

        // Right boundary
        bd.position.set(worldWidth, 0);
        Body rightBoundary = world.createBody(bd);
        rightBoundary.createFixture(fd);

        // Clean up
        horizontalEdge.dispose();
        verticalEdge.dispose();
    }

    private boolean isFixturePlayer(Fixture fixture) {
        return fixture.getUserData() != null && fixture.getUserData().equals("player");
    }

    private boolean isFixtureGround(Fixture fixture) {
        return fixture.getUserData() != null && fixture.getUserData().equals("ground");
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft();
            facingRight = false;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
            facingRight = true;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && isOnGround) {
            player.jump();
            isOnGround = false;  // Prevent further jumps until grounded again
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.playerBody.applyForceToCenter(0, -10, true);
        }
    }

    @Override
    public void show() {
        LevelLoader levelLoader = new LevelLoader();
        config = levelLoader.loadLevel(jsonPath);

        createCamera();
        createWorld(config.gravityX, config.gravityY);


        createBackgroundAndGround(config);

        player = new Player(world, x, y);

        System.out.println((Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        createWorldBounds(31, 31 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        // Set background sprite to cover the screen
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
    }

    @Override
    public void render(float delta) {
        handleInput();
        updateGameLogic(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        goToEastWoods();
        goToWestWoods();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawGameElements();


        batch.end();
        isMoving = false;
        debugRenderer.render(world, camera.combined);
    }

    public void updateGameLogic(float delta) {
        world.step(1/60f, 6, 2);
        stateTime += delta;
    }

    public void drawGameElements() {
        drawOtherElements();
        player.drawPlayer(batch, stateTime, isMoving, true , false, facingRight);
    }


    public void drawOtherElements() {

        backgroundSprite.draw(batch);
        for (Sprite groundSprite : groundSprites) {
            groundSprite.draw(batch);
        }
    }

    public void goToEastWoods(){
        Vector2 position = player.getPosition();
        if (position.x < 2 && position.y < 16) {
            game.setScreen(new EastWoodsBase(game, 28, 5, "levels/EastWoodsBase.json"));
        }
    }

    public void goToWestWoods(){
        Vector2 position = player.getPosition();
        if (position.x > 28 && position.y < 6) {
            game.setScreen(new WestWoodsBase(game, 2, 2, "levels/WestWoodsBase.json"));
        }
    }

    @Override
    public void resize(int i, int i1) {
        viewport.update(i, i1);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        backgroundTexture.dispose();

        player.dispose();

        for (Sprite groundSprite : groundSprites) {
            groundSprite.getTexture().dispose();
        }

    }
}
