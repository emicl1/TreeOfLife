package fel.gui;

import com.badlogic.gdx.Game;
import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WestWoodsPuzzle extends WestWoodsBase{
    public WestWoodsPuzzle(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }

    @Override
    public void goToFunctions() {
        goToWestWoodsBase();
        goToWestWoodsFinal();
    }

    public void goToWestWoodsBase() {
        if (player.getPosition().x < 2 && player.getPosition().y < 7) {
            Path path = Paths.get("src/main/resources/saveLevels/WestWoodsBase.json");
            if (path.toFile().exists()) {
                log.info("Loading WestWoodsBase saved file");
                game.setScreen(new WestWoodsBase(game, 28, 14, "src/main/resources/saveLevels/WestWoodsBase.json"));
            } else {
                log.info("Loading WestWoodsBase default file");
                game.setScreen(new WestWoodsBase(game, 28, 14, "levels/WestWoodsBase.json"));
            }
        }
    }

    public void goToWestWoodsFinal() {
        if (player.getPosition().x > 29 && player.getPosition().y > 15) {
            Path path = Paths.get("src/main/resources/saveLevels/WestWoodsFinal.json");
            if (path.toFile().exists()) {
                log.info("Loading WestWoodsFinal saved file");
                game.setScreen(new WestWoodsFinal(game, 2, 2, "src/main/resources/saveLevels/WestWoodsFinal.json"));
            } else {
                log.info("Loading WestWoodsFinal default file");
                game.setScreen(new WestWoodsFinal(game, 2, 2, "levels/WestWoodsFinal.json"));
            }
        }
    }

}
