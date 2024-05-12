package fel.gui;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fel.controller.MyGame;

import java.io.File;

public class MenuScreen implements Screen {
    public Stage stage;
    public Skin skin ;
    public MyGame game;


    public MenuScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        skin.add("white", new TextureRegionDrawable(new TextureRegion(whiteTexture)));
        pixmap.dispose();
        skin.load(Gdx.files.internal("menuuiskin.json"));
        Gdx.input.setInputProcessor(stage);
        createUI();
    }

    public void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Add widgets to the table
        TextButton NewGameButton = new TextButton("New Game", skin);
        TextButton SavedGameButton = new TextButton("Saved Game", skin);
        TextButton HelpButton = new TextButton("Help", skin);
        TextButton StoryScreen = new TextButton("Lore", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(NewGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(SavedGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(HelpButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(StoryScreen).fillX().uniformX();

        table.row().pad(10, 0, 10, 0);
        table.add(exitButton).fillX().uniformX();

        // Add listeners to buttons
        NewGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String workingDir = System.getProperty("user.dir");
                deleteRecursively(new File(workingDir + "/src/main/resources/saveLevels"));
                deleteRecursively(new File(workingDir + "/src/main/resources/savePlayer"));
                game.setScreen(new BaseScreen(game, 15, 4, "levels/BaseScreen.json"));
            }
        });

        SavedGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(new BaseScreen(game, 15, 4, "levels/BaseScreen.json"));
            }
        });

        HelpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new HelpScreen(game, new MenuScreen(game)));
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        StoryScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new StoryScreen(game));
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

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

    public void deleteRecursively(File file) {
        // Check if the file is read-only and try to make it writable
        if (!file.canWrite()) {
            file.setWritable(true);
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            assert files != null;
            for (File subfile : files) {
                subfile.setWritable(true);
            }

            for (File subfile : files) {
                deleteRecursively(subfile);
            }
        }

        // Attempt to delete the file or directory
        if (file.delete()) {
            System.out.println("Deleted: " + file.getPath());
        } else {
            System.out.println("Failed to delete: " + file.getPath());
            // Optional: Check why it failed
            if (file.exists()) {
                System.out.println("File still exists (possibly locked or in use)");
            }
        }
    }
}




