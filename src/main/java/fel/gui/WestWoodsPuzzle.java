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
    }

    public void goToWestWoodsBase() {
        if (player.getPosition().x < 2 && player.getPosition().y < 7) {
            Path path = Paths.get("saveLevels/WestWoodsBase.json");
            if (path.toFile().exists()) {
                game.setScreen(new WestWoodsBase(game, 28, 14, "saveLevels/WestWoodsBase.json"));
            } else {
                game.setScreen(new WestWoodsBase(game, 28, 14, "levels/WestWoodsBase.json"));
            }
        }
    }

}
