package fel.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import fel.controller.MyGame;

public class WinScreen extends MenuScreen {

    public WinScreen(MyGame game) {
        super(game);
    }


    @Override
    public void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label control = new Label("YOU WON\n" +
                "You have taken nutrients from the forest and \n" +
                "have grown strong enough to leave the forest\n" +
                "Credits: \n" +
                "Develop by: Alex Olivier Michaud\n" +
                "Graphics by: Caroline Iva Michaud alias Alfons\n" +
                "Proof reading by: Bará Metelcová", skin);

        table.row().pad(10, 0, 10, 0);
        table.add(control).fillX().uniformX();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game));
        }
    }

}
