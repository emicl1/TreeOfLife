package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class EastWoods1 extends EastWoodsBase{
    public EastWoods1(Game game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }

    @Override
    public void goToFunctions() {
        goToEastWoodsBase();
    }

    void goToEastWoodsBase() {
        if (player.getPosition().x > 29 && player.getPosition().y > 2) {
            game.setScreen(new EastWoodsBase(game, 2, 6, "levels/EastWoodsBase.json"));
        }
    }

}
