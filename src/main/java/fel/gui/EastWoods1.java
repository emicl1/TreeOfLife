package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import fel.controller.MyGame;

import java.nio.file.Path;
import java.nio.file.Paths;

public class EastWoods1 extends EastWoodsBase{
    public EastWoods1(MyGame game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }

    @Override
    public void goToFunctions() {
        goToEastWoodsBase();
    }

    void goToEastWoodsBase() {
        if (player.getPosition().x > 29 && player.getPosition().y > 2) {
            Path path = Paths.get("src/main/resources/saveLevels/EastWoodsBase.json");
            if (path.toFile().exists()) {
                game.setScreen(new EastWoodsBase(game, 2, 7, "src/main/resources/saveLevels/EastWoodsBase.json"));
            } else {
            game.setScreen(new EastWoodsBase(game, 2, 7, "levels/EastWoodsBase.json"));
            }
        }
    }

}
