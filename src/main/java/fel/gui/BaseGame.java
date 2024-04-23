package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseGame extends Game{

    protected Stage mainStage;
    private static BaseGame game;

    public void create(){
        mainStage = new Stage();
        initialize();
    }

    public abstract void initialize();

    public void render(){

    }

    public abstract void update(float dt);


}
