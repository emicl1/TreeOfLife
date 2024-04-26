package fel.controller;

import com.badlogic.gdx.Game;
import fel.gui.BaseScreen;

public class MyGame extends Game {



    @Override
    public void create() {
        this.setScreen(new BaseScreen(this, 15, 2, "levels/BaseScreen.json"));
    }
}
