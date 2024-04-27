package fel.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import fel.jsonFun.BigBugConfig;
import fel.jsonFun.GroundConfig;
import fel.jsonFun.LevelLoader;
import fel.jsonFun.SmallBugConfig;

import java.util.ArrayList;
import java.util.List;

public class EastWoodsBase extends BaseScreen {

    public List<EnemySmallBug> smallBugs;
    public List<EnemyBigBug> bigBugs;


    public EastWoodsBase(Game game, float x, float y, String jsonPath) {
        super(game, x, y, jsonPath);
    }

    @Override
    public void show() {
        LevelLoader levelLoader = new LevelLoader();
        config = levelLoader.loadLevel(jsonPath);

        createCamera();
        createWorld(config.gravityX, config.gravityY);

        createBackgroundAndGround(config);

        createPlayer();

        smallBugs = new ArrayList<>();
        bigBugs = new ArrayList<>();


        createEnemies();

        for (GroundConfig ground : config.ground){
            createGroundFromRectangularShape(world, config.groundImage, ground.x, ground.y, ground.x + ground.width, ground.y + ground.height);
        }

        System.out.println((Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        createWorldBounds(31, 31 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        // Set background sprite to cover the screen
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        camera.update();
    }

    public void createEnemies(){
        if (config.smallBugs != null){
            for (SmallBugConfig smallBugConfig : config.smallBugs){
                String[] paths = smallBugConfig.paths;
                Vector2 startPosition = new Vector2(smallBugConfig.x, smallBugConfig.y);
                EnemySmallBug enemy = new EnemySmallBug(world, paths, startPosition, smallBugConfig.leftBound, smallBugConfig.rightBound);
                enemy.loadAnimationEnemy();
                smallBugs.add(enemy);
            }
        }
        if (config.bigBugs != null){
            for (BigBugConfig bigBugConfig : config.bigBugs){
                String[] paths = bigBugConfig.paths;
                String[] attackPaths = bigBugConfig.attackPaths;
                Vector2 startPosition = new Vector2(bigBugConfig.x, bigBugConfig.y);
                EnemyBigBug enemy = new EnemyBigBug(world, paths, attackPaths, startPosition, bigBugConfig.leftBound, bigBugConfig.rightBound, 2.2f, 1.5f, 1.5f, 5f);
                enemy.loadAnimationEnemy();
                enemy.loadAttackAnimation();
                bigBugs.add(enemy);
            }
        }
    }


    @Override
    public void render(float delta) {
        handleInput(); // Handle user input
        updateGameLogic(delta); // Update game logic
        if (smallBugs != null){
            for(EnemySmallBug enemy : smallBugs){
                enemy.update(player.getPosition());
            }
        }
        if (bigBugs != null){
            for(EnemyBigBug enemy : bigBugs){
                enemy.update(player.getPosition());
            }
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        camera.update();
        goToFunctions();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawGameElements(); // Draw game elements
        if (smallBugs != null){
            for (EnemySmallBug enemy : smallBugs){
                enemy.draw(batch, stateTime);
            }
        }
        if (bigBugs != null){
            for (EnemyBigBug enemy : bigBugs){
                enemy.draw(batch, stateTime);
            }
        }
        batch.end();
        isMoving = false;
        debugRenderer.render(world, camera.combined); // Debug rendering
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }


    public void goToBase() {
        if (player.getPosition().x > 29 && player.getPosition().y < 10) {
            game.setScreen(new BaseScreen(game, 2, 2, "levels/BaseScreen.json"));
        }
    }

    public void goToEastWood1() {
        if (player.getPosition().x < 2 && player.getPosition().y < 6) {
            game.setScreen(new EastWoods1(game, 28, 5, "levels/EastWoods1.json"));
        }
    }

    public void goToFunctions() {
        goToBase();
        goToEastWood1();
    }

    @Override
    public void dispose() {
        super.dispose();
        for (EnemySmallBug enemy : smallBugs){
            enemy.dispose();
        }
        for (EnemyBigBug enemy : bigBugs){
            enemy.dispose();
        }
        player.dispose();
    }

}
