package fel.gui;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LevelScreen extends BaseScreen
{
    private Player player;
    private boolean win;

    public void initialize()
    {
        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "assets/water-border.jpg" );
        background.setSize(2000,1600);
        BaseActor.setWorldBounds(background);



        new GrassBlock(0,0, mainStage);
        new GrassBlock(100,0, mainStage);
        new GrassBlock(300,0, mainStage);
        new GrassBlock(450,0, mainStage);
        new GrassBlock(600,0, mainStage);
        new GrassBlock(800,0, mainStage);
        new GrassBlock(900,0, mainStage);
        new GrassBlock(1100,0, mainStage);

        player = new Player(200,200, mainStage);

    }

    public void update(float dt)
    {
        for (BaseActor rockActor : BaseActor.getList(mainStage, "Rock"))
            player.preventOverlap(rockActor);



    }
}