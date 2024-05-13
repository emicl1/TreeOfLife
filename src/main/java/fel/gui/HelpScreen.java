package fel.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import fel.controller.MyGame;

/**
 * HelpScreen class is a screen that displays the controls of the game.
 */
public class HelpScreen extends MenuScreen {
    private Screen previousScreen;

    public HelpScreen(MyGame game, Screen previousScreen) {
        super(game);
        this.previousScreen = previousScreen;
    }

    @Override
    public void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton backButton = new TextButton("Back", skin);
        Label control = new Label("Controls: \n" +
                "W - move up\n" +
                "A - move left\n" +
                "D - move right\n" +
                "E - interact\n" +
                "S - save game\n" +
                "C - craft\n" +
                "ESC - pause game", skin);


        table.row().pad(10, 0, 10, 0);
        table.add(backButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(control).fillX().uniformX();


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(previousScreen);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(previousScreen); // Or keep a reference to the paused game screen if necessary
        }
    }
}
