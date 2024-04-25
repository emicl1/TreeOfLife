package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import fel.jsonFun.LevelLoader;

public class WestWoodsBase extends BaseScreen {


    public WestWoodsBase(Game game, float x, float y) {
        super(game, x, y);
    }

    @Override
    public void show() {
        createCamera();
        createWorld(0, -30f);

        LevelLoader levelLoader = new LevelLoader();
        config = levelLoader.loadLevel("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/levels/WestWoodsBase.json");

        createBackgroundAndGround(config);

        loadAnimation();

        // Initialize the player
        createPlayer(x, y);

        createWorldBounds(31, 31 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));



        // Set background sprite to cover the screen
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
    }

    @Override
    public void render(float delta) {
        handleInput(); // Handle user input
        updateGameLogic(delta); // Update game logic

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        camera.update();
        goToBase();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawGameElements(); // Draw game elements
        batch.end();
        isMoving = false;
        debugRenderer.render(world, camera.combined); // Debug rendering
    }

    public void goToBase() {
        Vector2 position = player.getPosition();
        if (position.x < 2 && position.y < 6) {
            game.setScreen(new BaseScreen(game, 22, 2));
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }


    public void dispose() {
        super.dispose();
    }
}
