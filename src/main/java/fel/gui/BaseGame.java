package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseGame extends Game{

    protected Stage mainStage;
    private static BaseGame game;

    public BaseGame()
    {
        game = this;
    }

    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }

}
