package fel.gui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fel.controller.MyGame;

public class StoryScreen implements Screen{
    private Stage stage;
    private Skin skin;  // Assumes you have a skin for UI widgets
    private MyGame game;


    public StoryScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("menuuiskin.json"));  // Ensure you have this skin file
        createUI();
    }

    public void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        String storyText = "The world has been dying for years, a wolf came from afar years ago, and has been plaguing the whole groaning forest ever since. He poisoned\n" +
                "its waters and degraded its soil, so he has power over the forest and can torment it as he\n" +
                "his thoughts of his lost daughter who disappeared in these woods.\n" +
                "There is a legend among the beings who inhabit the groaning forest that one day there will come\n" +
                "the heir to the soul of the forest, and he will drive out the wolf and restore the forest to its strength.\n" +
                "One stormy night, an owl sits in its shelter, and sees a shooting star, which falls a short distance from its\n" +
                "she quickly runs after it, she notices that it is not a star but a soul, which used to be\n" +
                "and brings it to his house, where he lets it recover for another day. The soul has wandered out of the next forest and\n" +
                "now wants to escape, but it can't do that with thorns and rotten water everywhere, so it hears a story from an owl and about\n" +
                "forest, so she decides to help the forest so she can get home. She sees the state the Forest of Life is in,\n" +
                "all dried up and half dead, so she asks the owl what to do, he says he has to restore the water and bring nutrients to the forest.\n" +
                "So the hero goes to the Eastern Forest, where he solves two puzzles with which he gets a handle and\n" +
                "stick, which he uses to make a lever to run the old mill, which will restore the flow of water, and\n" +
                "the water is made drinkable again.  The local creatures will notice. And they advise him that at the beginning\n" +
                "...there's a cave where a sword is hidden, the hero goes to get it and follows the owl,\n" +
                "and he learns that there are nutrients in the western forest, which are divided into three parts. The hero goes to collect them,\n" +
                "but there are two types of enemies. Big bugs that run fast but are clumsy, and smaller ones\n" +
                "mosquitoes that attack quickly but die quickly. After getting the three parts and combining them. The hero recovers\n" +
                "the forest, but the wolf notices him and takes his savior, an owl, and climbs a tree with him, where he wants to\n" +
                "to kill it. Down below, one of the creatures quickly gives him a fireball, which he must put in the wolf's throat to\n" +
                "to kill him. He climbs up as fast as he can to overpower the wolf, but in the end he doesn't kill it, the wolf realizes his\n" +
                "blindness and runs away from the woods, and the Standing Les regains his old glory.";
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
