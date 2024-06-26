package fel.gui;

import fel.constants.Constants;
import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * EastWoodsFinal class is the final level of the East Woods.
 * A winning item is placed in this level.
 */
public class EastWoodsFinal extends EastWoodsBase {

    public EastWoodsFinal(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }

    @Override
    public void goToFunctions() {
        goToEastWoods1();
    }


    private void goToEastWoods1() {
        if (player.getPosition().x > 28 && player.getPosition().y < 5) {
            Path path = Paths.get(Constants.pathToSave + "EastWoods1.json");
            if (path.toFile().exists()) {
                log.info("Loading East Woods 1 saved level");
                game.setScreen(new EastWoods1(game, 2, 7, Constants.pathToSave + "EastWoods1.json"));
            } else {
                log.info("Loading East Woods 1 new level");
                game.setScreen(new EastWoods1(game, 2, 7, "levels/EastWoods1.json"));
            }
        }
    }
}
