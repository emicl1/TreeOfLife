package fel.gui;

import fel.constants.Constants;
import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * EastWoodsBase class that extends BaseScreen class
 * This class is used to create the EastWoodsBase screen in the game
 * which is the base screen for the East Woods levels
 */
public class EastWoodsBase extends BaseScreen {


    public EastWoodsBase(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }


    private void goToBase() {
        if (player.getPosition().x > 29 && player.getPosition().y < 10) {
            Path path = Paths.get(Constants.pathToSave + "BaseScreen.json");
            if (path.toFile().exists()) {
                log.info("Loading BaseScreen saved file");
                game.setScreen(new BaseScreen(game, 2, 3, Constants.pathToSave + "BaseScreen.json"));
            } else {
                log.info("Loading BaseScreen default file");
                game.setScreen(new BaseScreen(game, 2, 3, "levels/BaseScreen.json"));
            }
        }
    }

    private void goToEastWood1() {
        if (player.getPosition().x < 2 && player.getPosition().y < 6) {
            Path path = Paths.get(Constants.pathToSave + "EastWoods1.json");
            if (path.toFile().exists()) {
                log.info("Loading EastWoods1 saved file");
                game.setScreen(new EastWoods1(game, 28, 9, Constants.pathToSave + "EastWoods1.json"));
            } else {
                log.info("Loading EastWoods1 default file");
                game.setScreen(new EastWoods1(game, 28, 9, "levels/EastWoods1.json"));
            }
        }
    }

    @Override
    public void goToFunctions() {
        goToBase();
        goToEastWood1();
    }

}
