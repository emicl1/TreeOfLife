package tree.of.life;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


public class Player extends BaseActor{
    private String name;
    private int health;
    private int maxHealth;
    private int stage;

    public Player(float x, float y, Stage s) {
        super(x, y, s);
        String[] fileNames = {"assets/eastwoods/menší brouk1pohyb.png", "assets/eastwoods/menší brouk3pohyb.png",
                "assets/eastwoods/menší brouk4pohyb.png", "assets/eastwoods/menší brouk.png"};

        loadAnimationFromFiles(fileNames, 0.1f, true);

        //Action spin = Actions.parallel(Actions.alpha(1, 0.5f), Actions.rotateBy(360, 0.5f));
        //Action spin = Actions.rotateBy(20, 0.5f);

        //Action move = Actions.moveBy(30, 10, 0.5f);
        addAction(Actions.rotateBy(-20, 0.5f));
        //addAction(Actions.forever(move));
        //addAction(Actions.forever(spin));


    }





}
