package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import fel.jsonFun.GroundConfig;
import fel.jsonFun.LevelLoader;

public class EastWoodsBase extends BaseScreen {

    public Enemy enemy;


    public EastWoodsBase(Game game, float x, float y) {
        super(game, x, y);
    }

    @Override
    public void show() {
        createCamera();
        createWorld(0, -35f);

        LevelLoader levelLoader = new LevelLoader();
        config = levelLoader.loadLevel("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/levels/EastWoodsBase.json");

        createBackgroundAndGround(config);

        loadAnimation();

        createPlayer(x, y);

        String[] paths = {"eastwoods/mnbrouk1.png",
                "eastwoods/mnbrouk2.png",
                "eastwoods/mnbrouk3.png",
                "eastwoods/mnbrouk4.png"};

        Vector2 startPosition = new Vector2(6, 3);

        enemy = new Enemy(world, paths, startPosition, 8f, 24f);
        enemy.createEnemy();

        enemy.loadAnimationEnemy();

        for (GroundConfig ground : config.ground){
            createGroundFromRectangularShape(world, config.groundImage, ground.x, ground.y, ground.x + ground.width, ground.y + ground.height);
        }

        System.out.println((Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        createWorldBounds(31, 31 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        // Set background sprite to cover the screen
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        camera.update();
    }

    @Override
    public void render(float delta) {
        handleInput(); // Handle user input
        updateGameLogic(delta); // Update game logic
        enemy.update( player.getPosition());

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        camera.update();
        goToBase();

        Texture texture = new Texture(Gdx.files.internal("eastwoods/mnbrouk2.png"));
        Sprite sprite = new Sprite(texture);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        enemy.draw(batch, stateTime);
        drawGameElements(); // Draw game elements
        batch.draw(sprite, 0, 0);
        batch.end();
        isMoving = false;
        debugRenderer.render(world, camera.combined); // Debug rendering
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }


    public void goToBase() {
        if (player.getPosition().x > 29 && player.getPosition().y < 10) {
            game.setScreen(new BaseScreen(game, 2, 2));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        enemy.dispose();
    }

}
