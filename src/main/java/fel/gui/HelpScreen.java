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
    private Class<? extends Screen> previousScreenClass;
    private Object[] previousScreenParams;

    public HelpScreen(MyGame game, Class<? extends Screen> previousScreenClass, Object... previousScreenParams) {
        super(game);
        this.previousScreenClass = previousScreenClass;
        this.previousScreenParams = previousScreenParams;
        createLogger();
    }

    @Override
    public void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton backButton = new TextButton("Back to Game", skin);
        TextButton backToMenuButton = new TextButton("Back to Menu", skin);
        Label control = new Label("Controls: \n" +
                "W - move up\n" +
                "A - move left\n" +
                "D - move right\n" +
                "E - interact\n" +
                "S - save game\n" +
                "C - craft\n" +
                "ESC - pause game", skin);
        log.info("Labels and buttons set in HelpScreen");


        table.row().pad(10, 0, 10, 0);
        table.add(control).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(backToMenuButton).fillX().uniformX();
        log.info("Buttons in HelpScreen created");

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    Screen previousScreen = previousScreenClass.getDeclaredConstructor(getParameterTypes(previousScreenParams)).newInstance(previousScreenParams);
                    game.setScreen(previousScreen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        backToMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                log.info("From HelpScreen to Menu Screen");
                game.setScreen(new MenuScreen(game));
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
            try {
                Screen previousScreen = previousScreenClass.getDeclaredConstructor(getParameterTypes(previousScreenParams)).newInstance(previousScreenParams);
                game.setScreen(previousScreen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Class<?>[] getParameterTypes(Object[] params) {
        Class<?>[] types = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            types[i] = getWrapperClass(params[i].getClass());
        }
        return types;
    }

    private Class<?> getWrapperClass(Class<?> clazz) {
        if (clazz == Integer.class) return int.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Double.class) return double.class;
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Short.class) return short.class;
        if (clazz == Byte.class) return byte.class;
        if (clazz == Character.class) return char.class;
        return clazz;
    }
}
