package fel.gui;

import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WestWoodsFinal extends WestWoodsBase{
    public WestWoodsFinal(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }


    public void goToFunctions() {
        goToWestWoodsPuzzle();
    }


    public void goToWestWoodsPuzzle(){
        if (player.getPosition().x < 2 && player.getPosition().y < 7) {
            Path path = Paths.get("src/main/resources/saveLevels/WestWoodsPuzzle.json");
            if (path.toFile().exists()) {
                log.info("Loading WestWoodsPuzzle saved file");
                game.setScreen(new WestWoodsPuzzle(game, 28, 14, "src/main/resources/saveLevels/WestWoodsPuzzle.json"));
            } else {
                log.info("Loading WestWoodsPuzzle default file");
                game.setScreen(new WestWoodsPuzzle(game, 28, 14, "levels/WestWoodsPuzzle.json"));
            }
        }
    }
}
