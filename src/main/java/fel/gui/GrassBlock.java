package fel.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class GrassBlock extends BaseActor{

    public GrassBlock(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/kamen.png");
        setBoundaryPolygon(4);
        this.setSize(300, 300);
        createStaticBody();
        wakeUp();
    }
}
