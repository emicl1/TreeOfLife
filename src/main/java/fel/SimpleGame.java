package fel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;


public class SimpleGame extends Game {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private Body player;
    private SpriteBatch batch;
    private Sprite playerSprite, backgroundSprite;
    private Texture playerTexture, backgroundTexture, playerTextureMovement1, playerTextureMovement2, playerTextureMovement3, playerTextureMovement4;

    private Sprite[] groundSprites;
    private Body[] groundBodies;
    private boolean facingRight = true;

    private Animation<TextureRegion> animation;
    private float stateTime;

    private Animation<TextureRegion> walkRightAnimation;

    private boolean isOnGround;

    private boolean isMoving = false;

    private int groundContacts = 0;


    @Override
    public void create() {
        camera = new OrthographicCamera(30, 30 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        stateTime = 0f;  // Accumulate elapsed time

        world = new World(new Vector2(0, -9.7f), true);
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

        // Load textures
        playerTexture = new Texture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk2.png");
        backgroundTexture = new Texture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/homelocation/treetrunk.jpg");
        playerSprite = new Sprite(playerTexture);
        backgroundSprite = new Sprite(backgroundTexture);

        loadAnimation();

        // Initialize the player
        createPlayer();
        createGround();
        createWorldBounds(30, 30 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        // Set background sprite to cover the screen
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
    }

    private void loadAnimation(){
        playerTextureMovement1 = new Texture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk1.png");
        playerTextureMovement2 = new Texture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk2.png");
        playerTextureMovement3 = new Texture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk3.png");
        playerTextureMovement4 = new Texture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk4.png");

        Array<TextureRegion> frames = new Array<>();
        frames.add(new TextureRegion(playerTextureMovement1));
        frames.add(new TextureRegion(playerTextureMovement2));
        frames.add(new TextureRegion(playerTextureMovement3));
        frames.add(new TextureRegion(playerTextureMovement4));
        walkRightAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);

    }

    private void createPlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(15, 10);

        player = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1.5f, 1.1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;

        Fixture playerFixture = player.createFixture(fixtureDef);
        playerFixture.setUserData("player");
        shape.dispose();

    }

    private void createGround() {
        float groundWidth = 3f; // Width of each ground segment
        float groundHeight = 3f; // Height of each ground segment
        int numGroundSegments = 7; // Number of ground segments

        Texture groundTexture = new Texture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/kamen.png");
        Sprite[] groundSprites = new Sprite[numGroundSegments];
        Body[] groundBodies = new Body[numGroundSegments];

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
            groundBodies[i] = world.createBody(bodyDef);
            groundBodies[i].createFixture(fixtureDef);
            Fixture groundFixture = groundBodies[i].createFixture(fixtureDef);
            groundFixture.setUserData("ground");

            // Create and configure the sprite for each ground body
            groundSprites[i] = new Sprite(groundTexture);
            groundSprites[i].setSize(groundWidth, groundHeight);
            groundSprites[i].setPosition(groundBodies[i].getPosition().x - groundWidth / 2, groundBodies[i].getPosition().y - groundHeight / 2);
        }

        shape.dispose(); // Only dispose once after all bodies are created

        // Optionally store the ground bodies and sprites if needed for later use
        this.groundSprites = groundSprites; // Ensure this.groundSprites is defined in your class
        this.groundBodies = groundBodies; // Ensure this.groundBodies is defined in your class
    }

    private void createWorldBounds(float worldWidth, float worldHeight) {
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

    @Override
    public void render() {
        float PPM = 250f;
        handleInput();
        world.step(1/60f, 6, 2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime(); // Update the state time
        TextureRegion currentFrame;

        if (isMoving) {
            currentFrame = walkRightAnimation.getKeyFrame(stateTime, isMoving);
        }else {
            currentFrame = walkRightAnimation.getKeyFrame(0, false);
        }
        isMoving = false;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        backgroundSprite.draw(batch);

        float playerX = player.getPosition().x - currentFrame.getRegionWidth() * 0.5f / PPM;
        float playerY = player.getPosition().y - currentFrame.getRegionHeight() * 0.5f / PPM;
        float width = currentFrame.getRegionWidth() / PPM;
        float height = currentFrame.getRegionHeight() / PPM + 0.5f;

        if (facingRight) {
            batch.draw(currentFrame, playerX, playerY, width, height);
        } else {
            batch.draw(currentFrame, playerX + width, playerY, -width, height); // Draw flipped
        }
        for (Sprite groundSprite : groundSprites) {
            groundSprite.draw(batch);
        }
        batch.end();

        camera.update();
        debugRenderer.render(world, camera.combined);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.applyForceToCenter(-80, 0, true);
            //player.applyLinearImpulse(new Vector2(-30, 0), player.getWorldCenter(), true);
            facingRight = false;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            //player.applyLinearImpulse(new Vector2(30, 0), player.getWorldCenter(), true);
            player.applyForceToCenter(80, 0, true);
            facingRight = true;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && isOnGround) {
            System.out.println("Jumping");
            player.applyLinearImpulse(new Vector2(0, 55f), player.getWorldCenter(), true);
            isOnGround = true;  // Prevent further jumps until grounded again
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.applyForceToCenter(0, -10, true);
        }
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        playerTexture.dispose();
        backgroundTexture.dispose();

        for (Sprite groundSprite : groundSprites) {
            groundSprite.getTexture().dispose();
        }
    }

}
