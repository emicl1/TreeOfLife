package fel.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import fel.controller.TreeOfLife;
import fel.gui.BaseScreen;
import fel.gui.LevelScreen;
import fel.gui.BaseActor;

public class MenuScreen extends BaseScreen {
    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/homelocation/treetrunk.jpg");
        background.setSize(2000, 1600);


    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Keys.S))
            TreeOfLife.setActiveScreen(new LevelScreen());
    }
}
