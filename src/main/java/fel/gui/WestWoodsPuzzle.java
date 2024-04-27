package fel.gui;

import com.badlogic.gdx.Game;

public class WestWoodsPuzzle extends WestWoodsBase{
    public WestWoodsPuzzle(Game game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }

    @Override
    public void goToFunctions() {
        goToWestWoods();
    }

    public void goToWestWoodsBase() {
        if (player.getPosition().x < 2 && player.getPosition().y > 2) {
            game.setScreen(new WestWoodsBase(game, 28, 6, "levels/WestWoodsBase.json"));
        }
    }

}
