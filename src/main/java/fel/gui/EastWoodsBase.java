package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import fel.jsonFun.BigBugConfig;
import fel.jsonFun.GroundConfig;
import fel.jsonFun.LevelLoader;
import fel.jsonFun.SmallBugConfig;

import java.util.ArrayList;
import java.util.List;

public class EastWoodsBase extends BaseScreen {


    public EastWoodsBase(Game game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }


    public void goToBase() {
        if (player.getPosition().x > 29 && player.getPosition().y < 10) {
            game.setScreen(new BaseScreen(game, 2, 2, "levels/BaseScreen.json"));
        }
    }

    public void goToEastWood1() {
        if (player.getPosition().x < 2 && player.getPosition().y < 6) {
            game.setScreen(new EastWoods1(game, 27, 6, "levels/EastWoods1.json"));
        }
    }

    @Override
    public void goToFunctions() {
        goToBase();
        goToEastWood1();
    }

}
