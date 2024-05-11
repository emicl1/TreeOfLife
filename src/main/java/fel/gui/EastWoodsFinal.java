package fel.gui;

import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;

public class EastWoodsFinal extends EastWoodsBase{
    public EastWoodsFinal(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }


    public void goToFunctions() {
        goToEastWoods1();
    }


    public void goToEastWoods1(){


        if (player.getPosition().x >28  && player.getPosition().y < 5) {
            Path path = Paths.get("src/main/resources/saveLevels/EastWoods1.json");
            if (path.toFile().exists()) {
                log.info("Loading East Woods 1 saved level");
                game.setScreen(new EastWoods1(game, 2, 7, "src/main/resources/saveLevels/EastWoods1.json"));
            } else {
                log.info("Loading East Woods 1 new level");
                game.setScreen(new EastWoods1(game, 2, 7, "levels/EastWoods1.json"));
            }
        }
    }
}
