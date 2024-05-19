package fel.gui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fel.controller.MyGame;


/**
 * StoryScreen class that represents the story screen in the game
 * The story screen has a story text and a back button
 * It uses a very functional scroll pane :)

 */
public class StoryScreen implements Screen{
    private Stage stage;
    private Skin skin;
    private MyGame game;


    public StoryScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("menuuiskin.json"));  // Ensure you have this skin file
        createUI();
    }


    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        String storyText = "The world has been dying for years, a wolf came from afar years ago, and has been plaguing the Whispering Woods ever since. He poisoned" +
                "its waters and degraded its soil, so he has power over the forest and can torment it, in order to avenge" +
                "his lost daughter who disappeared there.\n" +
                "There is a legend among the beings who inhabit the Whispering Woods that one day there will come" +
                "the heir to the soul of the forest, and he will drive out the wolf and restore the forest to its former strength.\n" +
                "One stormy night, an owl sits in its shelter, and sees a shooting star, which falls a short distance from her." +
                " She quickly runs after it and notices that it's not a star but a soul and brings it to her house, " +
                "where she lets it recover for another day.\nThe soul had wandered out of the neighbouring forest and" +
                "wanted to escape from it, but couldn't because of the thorns and filthy water everywhere. It hears out story about the forest from the owl known as Ori." +
                "It decides to help the forest so it can get home. It sees the state the Whispering Woods are in," +
                "all dried up and almost lifeless. When asked, Ori shares what to do. It has to restore the water and bring nutrients to the forest. The soul becomes" +
                "the hero of our story and goes to the Eastern Forest, where he solves two puzzles and obtains a handle and" +
                "a stick, which he uses to make a lever needed to run the old mill that will restore the flow of water, make it potable again.\n" +
                "The inhabitants notice the change and they give him advice that there's a cave where is a hidden sword. The hero goes to retrieve it and follows Ori," +
                "while he learns that there are nutrients in the Western Forest, which are divided into three parts. The hero sets off to collect them," +
                "but encounters two types of enemies. There are big bugs that run fast but are clumsy, and small" +
                "mosquitoes that attack quickly but can be killed easily. After getting the three parts and combining them, the hero restores" +
                "the forest.\nThe wolf notices him and kidnaps his savior, Ori, and takes her up a tree, where he wants to" +
                "to kill her. Down below, one of the creatures quickly gives him a fireball, which he must put down the wolf's throat in order to" +
                "finish him. He climbs up as fast as he can, overpowers the wolf, but in the end he doesn't eliminate him, because the wolf realizes his" +
                "weakness and runs away from the woods, and the Whispering Woods regain their former glory.";
        Label storyLabel = new Label(storyText, skin);
        storyLabel.setWrap(true);

        ScrollPane scrollPane = new ScrollPane(storyLabel);
        scrollPane.setSmoothScrolling(true);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setStyle(createScrollPaneStyle());

        TextButton backButton = new TextButton("Back", skin);

        float preferredWidth = Gdx.graphics.getWidth() * 0.5f;
        table.add(scrollPane).width(preferredWidth).center().expandX();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(new MenuScreen(game));
            }
        });
    }

    private ScrollPane.ScrollPaneStyle createScrollPaneStyle() {
        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();
        // Optionally set background, hScrollKnob, etc., here if needed
        return style;
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
