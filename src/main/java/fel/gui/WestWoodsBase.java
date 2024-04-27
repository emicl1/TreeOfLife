package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import fel.jsonFun.LevelLoader;

public class WestWoodsBase extends BaseScreen {


    public WestWoodsBase(Game game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }


    @Override
    public void render(float delta) {
        handleInput(); // Handle user input
        updateGameLogic(delta); // Update game logic

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        goToFunctions();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawGameElements(); // Draw game elements
        batch.end();
        isMoving = false;
        debugRenderer.render(world, camera.combined); // Debug rendering
    }

    @Override
    public void createPlayer(){
        System.out.println("Creating player");
        player = new Player(world, x, y, "player/PlayerWestWoodsBase.json");
    }

    public void goToFunctions() {
        GoToPuzzle();
        goToBase();
    }


    public void GoToPuzzle() {
        Vector2 position = player.getPosition();
        if (position.x > 26 && position.y > 15) {
            game.setScreen(new WestWoodsPuzzle(game, 2, 2, "levels/WestWoodsPuzzle.json"));
        }
    }


    public void goToBase() {
        Vector2 position = player.getPosition();
        if (position.x < 2 && position.y < 6) {
            game.setScreen(new BaseScreen(game, 24, 2, "levels/BaseScreen.json"));
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
