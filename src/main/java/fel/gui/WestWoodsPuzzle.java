package fel.gui;

import fel.constants.Constants;
import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * WestWoodsPuzzle class is the puzzle level of the West Woods.
 */

public class WestWoodsPuzzle extends WestWoodsBase{
    public WestWoodsPuzzle(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }

    @Override
    public void goToFunctions() {
        goToWestWoodsBase();
        goToWestWoodsFinal();
    }

    private void goToWestWoodsBase() {
        if (player.getPosition().x < 2 && player.getPosition().y < 7) {
            Path path = Paths.get(Constants.pathToSave + "WestWoodsBase.json");
            if (path.toFile().exists()) {
                log.info("Loading WestWoodsBase saved file");
                game.setScreen(new WestWoodsBase(game, 26, 14, Constants.pathToSave + "WestWoodsBase.json"));
            } else {
                log.info("Loading WestWoodsBase default file");
                game.setScreen(new WestWoodsBase(game, 26, 14, "levels/WestWoodsBase.json"));
            }
        }
    }

    private void goToWestWoodsFinal() {
        if (player.getPosition().x > 29 && player.getPosition().y > 15) {
            Path path = Paths.get(Constants.pathToSave + "WestWoodsFinal.json");
            if (path.toFile().exists()) {
                log.info("Loading WestWoodsFinal saved file");
                game.setScreen(new WestWoodsFinal(game, 2, 2, Constants.pathToSave + "WestWoodsFinal.json"));
            } else {
                log.info("Loading WestWoodsFinal default file");
                game.setScreen(new WestWoodsFinal(game, 2, 2, "levels/WestWoodsFinal.json"));
            }
        }
    }

}
