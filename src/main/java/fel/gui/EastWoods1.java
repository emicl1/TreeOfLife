package fel.gui;

import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * East Woods 1 class that extends East Woods Base class
 * This class is used to create level of the East Woods
 */
public class EastWoods1 extends EastWoodsBase{
    public EastWoods1(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }

    @Override
    public void goToFunctions() {
        goToEastWoodsBase();
        goToEastWoodsFinal();
    }

    private void goToEastWoodsBase() {
        if (player.getPosition().x > 29 && player.getPosition().y > 2) {
            Path path = Paths.get("src/main/resources/saveLevels/EastWoodsBase.json");
            if (path.toFile().exists()) {
                log.info("Loading East Woods Base saved level");
                game.setScreen(new EastWoodsBase(game, 2, 7, "src/main/resources/saveLevels/EastWoodsBase.json"));
            } else {
                log.info("Loading East Woods Base new level");
            game.setScreen(new EastWoodsBase(game, 2, 7, "levels/EastWoodsBase.json"));
            }
        }
    }

    private void goToEastWoodsFinal() {
        if (player.getPosition().x < 2 && player.getPosition().y < 7) {
            Path path = Paths.get("src/main/resources/saveLevels/EastWoodsFinal.json");
            if (path.toFile().exists()) {
                log.info("Loading East Woods Final saved level");
                game.setScreen(new EastWoodsFinal(game, 27, 3, "src/main/resources/saveLevels/EastWoodsFinal.json"));
            } else {
                log.info("Loading East Woods Final new level");
                game.setScreen(new EastWoodsFinal(game, 27, 3, "levels/EastWoodsFinal.json"));
            }
        }
    }

}
