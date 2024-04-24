package fel.gui;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


public class Player extends BaseActor {
    private Animation stand;
    private Animation walk;

    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;

    private Animation jump;
    private float jumpSpeed;

    boolean isMoving;

    public Player(float x, float y, Stage s) {
        super(x, y, s);
        String[] fileNames = {"/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk1.png",
                "/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk2.png",
                "/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk3.png",
                "/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk4.png"};

        loadAnimationFromFiles(fileNames, 0.1f, true);

        Texture t = new Texture(Gdx.files.internal("/home/alex/IdeaProjects/TreeOfLife/src/main/resources/eastwoods/mnbrouk2.png"));

        setMaxSpeed(300);
        setDeceleration(600);

        createDynamicBody();
        wakeUp();
        alignCamera();

        maxHorizontalSpeed = 100;
        walkAcceleration   = 200;
        walkDeceleration   = 200;
        gravity            = 700;
        maxVerticalSpeed   = 1000;


        setBoundaryPolygon(8);
        //Action spin = Actions.rotateBy(20, 0.5f);

        jumpSpeed = 450;

         isMoving = false;

    }

    public void act(float dt) {
        {
            super.act(dt);
            applyPhysics(dt);
            //setAnimationPaused(false);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ) {
                applyLinearImpulse(0.1f, 0);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                applyLinearImpulse(-0.1f, 0);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP) ) {
                applyLinearImpulse(0, 20000);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                applyLinearImpulse(0, -20000);

            }
            alignCamera();
            boundToWorld();
        }
    }



    public void jump()
    {
        velocityVec.y = jumpSpeed;
    }

    public boolean isFalling()
    {
        return (velocityVec.y < 0);
    }

    public void spring()
    {
        velocityVec.y = 1.5f * jumpSpeed;
    }

    public boolean isJumping()
    {
        return (velocityVec.y > 0);
    }

    public void update(float dt){

    }





}


