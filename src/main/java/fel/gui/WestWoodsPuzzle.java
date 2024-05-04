package fel.gui;

import com.badlogic.gdx.Game;
import fel.controller.MyGame;

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
            game.setScreen(new WestWoodsBase(game, 28, 15, "levels/WestWoodsBase.json"));
        }
    }

}
