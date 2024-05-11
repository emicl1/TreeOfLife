package fel.gui;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen {
    private Stage stage;
    private Skin skin;

    public MenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Assume you have a "uiskin.json" in your assets directory
        skin = new Skin(Gdx.files.internal("menuuiskin.json"));

        // Create UI
        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Add widgets to the table
        TextButton NewGameButton = new TextButton("New Game", skin);
        TextButton SavedGameButton = new TextButton("Saved Game", skin);
        TextButton HelpButton = new TextButton("Help", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(NewGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(SavedGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(HelpButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exitButton).fillX().uniformX();

        // Add listeners to buttons
        NewGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Start new game
            }
        });

        SavedGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Load saved game
            }
        });

        HelpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Show help screen
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
