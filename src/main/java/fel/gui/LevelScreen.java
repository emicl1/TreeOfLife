package fel.gui;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LevelScreen extends BaseScreen

{
    private Player player;

    public void initialize()
    {
        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "/home/alex/IdeaProjects/TreeOfLife/src/main/resources/homelocation/treetrunk.jpg" );
        background.setSize(2000,1600);
        BaseActor.setWorldBounds(background);

        // generate grass blocks for the ground

        new GrassBlock(0,00, mainStage);
        new GrassBlock(300,00, mainStage);
        new GrassBlock(600,00, mainStage);
        new GrassBlock(900,00, mainStage);
        new GrassBlock(1200,00, mainStage);
        new GrassBlock(1500,00, mainStage);
        new GrassBlock(1800,00, mainStage);

        player = new Player(600,400, mainStage);



    }

    public void update(float dt){


    }

    public void render(float dt) {
        super.render(dt);

    }


}