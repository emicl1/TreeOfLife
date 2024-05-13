package fel.gui;

import com.badlogic.gdx.math.Vector2;
import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;


public class WestWoodsBase extends BaseScreen {


    public WestWoodsBase(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }


    @Override
    public void createPlayer(){

        Path path = Paths.get("src/main/resources/savePlayer/PlayerWestWoodsBase.json");

        if (path.toFile().exists()) {
            log.info("Loading Player saved file");
            player = new Player(world, x, y, "src/main/resources/savePlayer/PlayerWestWoodsBase.json", log);
        } else {
            log.info("Loading Player default file");
            player = new Player(world, x, y, "player/PlayerWestWoodsBase.json", log);
        }
    }


    @Override
    public void goToFunctions() {
        GoToPuzzle();
        goToBase();
    }


    private void GoToPuzzle() {
        Vector2 position = player.getPosition();
        if (position.x > 28 && position.y > 14) {
            Path path = Paths.get("src/main/resources/saveLevels/WestWoodsPuzzle.json");
            if (path.toFile().exists()) {
                log.info("Loading WestWoodsPuzzle saved file");
                game.setScreen(new WestWoodsPuzzle(game, 2, 2, "src/main/resources/saveLevels/WestWoodsPuzzle.json"));
            } else {
                log.info("Loading WestWoodsPuzzle default file");
                game.setScreen(new WestWoodsPuzzle(game, 2, 2, "levels/WestWoodsPuzzle.json"));
            }
        }
    }


    private void goToBase() {
        Vector2 position = player.getPosition();
        if (position.x < 2 && position.y < 6) {
            Path path = Paths.get("src/main/resources/saveLevels/BaseScreen.json");
            if (path.toFile().exists()) {
                log.info("Loading BaseScreen saved file");
                game.setScreen(new BaseScreen(game, 24, 4, "src/main/resources/saveLevels/BaseScreen.json"));
            } else {
                log.info("Loading BaseScreen default file");
                game.setScreen(new BaseScreen(game, 24, 4, "levels/BaseScreen.json"));
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }

}
